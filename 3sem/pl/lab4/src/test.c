//
// Created by eren on 12/17/23.
//

#include "mem.h"
#include "mem_internals.h"
#include "util.h"
#include <assert.h>

static struct block_header* block_get_header(void* contents) {
    return (struct block_header*) (((uint8_t*)contents)-offsetof(struct block_header, contents));
}

void testOne() {
    printf("1 - Test\n");

    void* heap = heap_init(REGION_MIN_SIZE);
    assert(heap != NULL);

    void* content = _malloc(16);
    struct block_header* header = block_get_header(content);
    assert(header->is_free == false);
    assert(header->capacity.bytes == 24);

    heap_term();
    printf("Test 1 - Successful\n\n");
}

void testTwo() {
    printf("2 - Test\n");

    void* heap = heap_init(REGION_MIN_SIZE);
    assert(heap != NULL);

    void* content1 = _malloc(24);
    void* content2 = _malloc(100);
    assert(content1 != NULL);
    assert(content2 != NULL);

    struct block_header* header1 = block_get_header(content1);
    struct block_header* header2 = block_get_header(content2);
    _free(content1);
    assert(header1->is_free == true);
    assert(header2->is_free == false);
    assert(header1->next == header2);

    heap_term();
    printf("Test 2 - Successful\n\n");
}

void testThree() {
    printf("3 - Test\n");

    void* heap = heap_init(REGION_MIN_SIZE);
    assert(heap != NULL);

    void* content1 = _malloc(24);
    void* content2 = _malloc(24);
    void* content3 = _malloc(100);
    void* content4 = _malloc(25);

    struct block_header* header1 = block_get_header(content1);
    struct block_header* header2 = block_get_header(content2);
    struct block_header* header3 = block_get_header(content3);
    struct block_header* header4 = block_get_header(content4);
    _free(content1);
    _free(content4);
    assert(header1->is_free == true);
    assert(header2->is_free == false);
    assert(header3->is_free == false);
    assert(header4->is_free == true);

    heap_term();
    printf("Test 3 - Successful\n\n");
}

void testFour() {
    printf("4 - Test\n");

    void* heap = heap_init(REGION_MIN_SIZE);
    assert(heap != NULL);

    void* content = _malloc(REGION_MIN_SIZE);
    assert(content != NULL);
    struct block_header* header = block_get_header(content);
    assert(header->capacity.bytes == REGION_MIN_SIZE);

    heap_term();
    printf("Test 4 - Successful\n\n");
}

void testFive() {
    printf("5 - Testing\n");

    void* heap = heap_init(0);
    void* space = mmap(HEAP_START + REGION_MIN_SIZE, 24, 0, MAP_PRIVATE | 0x20, -1, 0);
    assert(heap != NULL);
    assert(space != NULL);

    void* content1 = _malloc(REGION_MIN_SIZE - offsetof(struct block_header, contents));
    void* separate_content = _malloc(REGION_MIN_SIZE - offsetof(struct block_header, contents));
    assert(content1 != NULL);
    assert(separate_content != NULL);

    struct block_header* separate_header = block_get_header(separate_content);
    munmap(space, 24);

    struct block_header* header1 = (struct block_header*) heap;

    assert(header1->next == separate_header);
    assert((void*) header1 + offsetof(struct block_header, contents) + header1->capacity.bytes != separate_header);

    heap_term();
    printf("Test 5 - Successful\n\n");
}