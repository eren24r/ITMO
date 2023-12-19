#define _DEFAULT_SOURCE

#include <unistd.h>

#include "mem_internals.h"
#include "mem.h"
#include "util.h"

void debug_block(struct block_header* b, const char* fmt, ... );
void debug(const char* fmt, ... );

extern inline block_size size_from_capacity( block_capacity cap );
extern inline block_capacity capacity_from_size( block_size sz );

static bool            block_is_big_enough( size_t query, struct block_header* block ) { return block->capacity.bytes >= query; }
static size_t          pages_count   ( size_t mem )                      { return mem / getpagesize() + ((mem % getpagesize()) > 0); }
static size_t          round_pages   ( size_t mem )                      { return getpagesize() * pages_count( mem ) ; }

static void block_init( void* restrict addr, block_size block_sz, void* restrict next ) {
    *((struct block_header*)addr) = (struct block_header) {
            .next = next,
            .capacity = capacity_from_size(block_sz),
            .is_free = true
    };
}

static size_t region_actual_size( size_t query ) { return size_max( round_pages( query ), REGION_MIN_SIZE ); }

extern inline bool region_is_invalid( const struct region* r );



static void* map_pages(void const* addr, size_t length, int additional_flags) {
    return mmap( (void*) addr, length, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS | additional_flags , -1, 0 );
}

/*  аллоцировать регион памяти и инициализировать его блоком */
static struct region alloc_region( void const * addr, size_t query ) {
    size_t reg_size = region_actual_size(size_from_capacity((block_capacity){.bytes = query}).bytes);
    void* mapped_reg = map_pages(addr, reg_size,  MAP_FIXED_NOREPLACE);

    if (mapped_reg == MAP_FAILED) {
        mapped_reg = map_pages(addr, reg_size, 0);
        if (mapped_reg == MAP_FAILED) {
            return REGION_INVALID;
        }
    }

    block_init(mapped_reg, (block_size) {.bytes = reg_size}, NULL);
    return (struct region) {.addr = mapped_reg, .size = reg_size, .extends = mapped_reg == addr};
}

static void* block_after( struct block_header const* block );

void* heap_init( size_t initial ) {
    const struct region reg = alloc_region( HEAP_START, initial );
    if ( region_is_invalid(&reg) ) return NULL;

    return reg.addr;
}

#define BLOCK_MIN_CAPACITY 24

/*  --- Разделение блоков (если найденный свободный блок слишком большой)--- */

static bool block_splittable( struct block_header* restrict block, size_t query) {
    return block-> is_free && query + offsetof( struct block_header, contents ) + BLOCK_MIN_CAPACITY <= block->capacity.bytes;
}

static bool split_if_too_big( struct block_header* block, size_t query ) {
    query = size_max(query, BLOCK_MIN_CAPACITY);

    if (!block || !block_splittable(block, query)) { return false; }

    block_size old_block_size = size_from_capacity((block_capacity) {.bytes = query});
    block_size new_block_size = (block_size) {size_from_capacity(block->capacity).bytes - old_block_size.bytes};

    block_init((void *)block + old_block_size.bytes, new_block_size, block->next);
    block_init(block, old_block_size, (void *)block + old_block_size.bytes);

    return true;
}


/*  --- Слияние соседних свободных блоков --- */

static void* block_after( struct block_header const* block )              {
    return  (void*) (block->contents + block->capacity.bytes);
}
static bool blocks_continuous (
        struct block_header const* fst,
        struct block_header const* snd ) {
    return (void*)snd == block_after(fst);
}

static bool mergeable(struct block_header const* restrict fst, struct block_header const* restrict snd) {
    return fst->is_free && snd->is_free && blocks_continuous( fst, snd ) ;
}

static bool try_merge_with_next( struct block_header* block ) {
    struct block_header* nblock = block->next;
    if (block == NULL || nblock == NULL) {
        return false;
    }

    if (mergeable(block, nblock)) {
        block_init(block, (block_size) {.bytes = size_from_capacity(block->capacity).bytes +
                                                 size_from_capacity(nblock->capacity).bytes}, nblock->next);
        return true;
    } else return false;
}


/*  освободить всю память, выделенную под кучу */
void heap_term( ) {
    void* cur_reg_frst = HEAP_START;
    struct block_header* current_block = (struct block_header*) cur_reg_frst;
    size_t cur_reg_s = 0;

    while (current_block) {
        struct block_header* next_block = current_block->next;
        cur_reg_s += size_from_capacity(current_block->capacity).bytes;

        if (!next_block) munmap(cur_reg_frst, cur_reg_s);
        else if(block_after(current_block) != next_block){
            munmap(cur_reg_frst, cur_reg_s);
            cur_reg_frst = next_block;
            cur_reg_s = 0;
        }

        current_block = next_block;
    }
}



/*  --- ... ecли размера кучи хватает --- */

struct block_search_result {
    enum {BSR_FOUND_GOOD_BLOCK, BSR_REACHED_END_NOT_FOUND, BSR_CORRUPTED} type;
    struct block_header* block;
};


static struct block_search_result find_good_or_last  ( struct block_header* restrict block, size_t sz )    {
    if (!block) {
        return (struct block_search_result) {.type = BSR_CORRUPTED};
    }
    while (true) {
        if (block->is_free) {
            while (try_merge_with_next(block)) {}
            if (block_is_big_enough(sz, block)) {
                return (struct block_search_result) {.type = BSR_FOUND_GOOD_BLOCK, .block = block};
            }
        }
        if (block->next == NULL){
            break;
        }
        block = block->next;
    }
    return (struct block_search_result) {.type = BSR_REACHED_END_NOT_FOUND, .block = block};
}

/*  Попробовать выделить память в куче начиная с блока `block` не пытаясь расширить кучу
 Можно переиспользовать как только кучу расширили. */
static struct block_search_result try_memalloc_existing ( size_t query, struct block_header* block ) {
    struct block_search_result res = find_good_or_last(block, query);

    if (res.type == BSR_CORRUPTED || res.type == BSR_REACHED_END_NOT_FOUND) {
        return res;
    }

    split_if_too_big(res.block, query);
    res.block->is_free = false;
    return res;
}



static struct block_header* grow_heap( struct block_header* restrict last, size_t query ) {
    if (last == NULL) {
        return NULL;
    }

    struct region n_reg = alloc_region(block_after(last), query);
    if (region_is_invalid(&n_reg)) {
        return NULL;
    }

    last->next = n_reg.addr;
    if (n_reg.extends && try_merge_with_next(last)) {
        return last;
    }
    return last->next;
}

/*  Реализует основную логику malloc и возвращает заголовок выделенного блока */
static struct block_header* memalloc( size_t query, struct block_header* heap_start) {
    if (heap_start == NULL) {
        return NULL;
    }

    struct block_search_result res = try_memalloc_existing(query, heap_start);

    if (res.type != BSR_CORRUPTED) {
        if (res.type == BSR_FOUND_GOOD_BLOCK) {
            return res.block;
        }
        struct block_header* growed_block = grow_heap(res.block, query);
        if (growed_block == NULL) {
            return NULL;
        }
        res = try_memalloc_existing(query, growed_block);
        if (res.type == BSR_FOUND_GOOD_BLOCK) {
            return res.block;
        }
    }
    return NULL;
}

void* _malloc( size_t query ) {
    struct block_header* const addr = memalloc( query, (struct block_header*) HEAP_START );
    if (addr) {
        return addr->contents;
    }
    else {
        return NULL;
    }
}

static struct block_header* block_get_header(void* contents) {
    return (struct block_header*) (((uint8_t*)contents)-offsetof(struct block_header, contents));
}

void _free( void* mem ) {
    if (!mem) {
        return;
    }
    struct block_header* hd = block_get_header( mem );
    hd->is_free = true;
    try_merge_with_next(hd);
}
