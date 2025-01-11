#include "image.h"
#include <stddef.h>
#include <stdlib.h>
#include <string.h>

struct image empty_image = {{0, 0}, NULL};

static bool dimensions_equals(const struct dimensions a, const struct dimensions b);
static bool pixel_equals(const struct pixel a, const struct pixel b);

// Создает struct image, если не удалось выделить место в куче, то возвращает {{0,0}, NULL}
struct image image_create(struct dimensions size) {

	struct image image;
	image.size = size;
	image.data = malloc(size.x * size.y * sizeof(struct pixel));

    if (image.data == NULL) {
		return empty_image;
	}

    return image;
}

// Очищает struct image, если она уже не пуста
void image_destroy( struct image* image ) {

    if (image ->data != NULL) {
		free(image -> data);
		image -> data = NULL;
	}
}

bool image_equals( const struct image a, const struct image b ) {

    if (!dimensions_equals(a.size, b.size)) {
        return false;
    }

    for (size_t i = 0; i < a.size.x*a.size.y; i++) {
        if (!pixel_equals(a.data[i], b.data[i])) {
            return false;
        }
    }

    return true;
}

static bool dimensions_equals(const struct dimensions a, const struct dimensions b) {

    return (a.x == b.x) && (a.y == b.y);
}

static bool pixel_equals(const struct pixel a, const struct pixel b) {

    return (a.r == b.r) && (a.g == b.g) && (a.b == b.b);
}


uint32_t calculate_padding(uint32_t width, uint16_t bitCount) {

    int64_t x = width * bitCount / 8;
    return (4 - (x % 4)) % 4;
}

long get_file_size(FILE* file) {

    if (file == NULL) {
        return -1;
    }

    long curr = ftell(file);

    if (curr == -1) {
        return -1;
    }


    if (fseek(file, 0, SEEK_END) != 0) {
        return -1;
    }

    long size = ftell(file);

    if (size == -1) {
        return -1;
    }

    if (fseek(file, curr, SEEK_SET) != 0) {
        return -1;
    };

    return size;
}

bool string_equals(const char* a, const char* b) {

    int x = strcmp(a, b);

    if (x == 0) {
        return true;
    }

    return false;
}


FILE* open_file(const char* file_path, const char* mode) {

    FILE* file = fopen(file_path, mode);
    return file;
}

bool close_file(FILE* file) {
    if (file) {
        fclose(file);
        return true;
    }
    return false;
}


typedef struct {
    const char* name;
    struct image (*function)(const struct image source);
} action;

static struct image rotate_cw90 (const struct image source);
static struct image rotate_ccw90 (const struct image source);
static struct image flip_vertical (const struct image source);
static struct image flip_horizontal (const struct image source);
static struct image none(const struct image source);

static action transforms[5] = {
    {"none", none},
    {"cw90", rotate_cw90},
    {"ccw90", rotate_ccw90},
    {"flipv", flip_vertical},
    {"fliph", flip_horizontal}
};

struct image transform(struct image const source, const char* action) {
    for (size_t i = 0; i < sizeof(transforms)/sizeof(transforms[0]); i++) {
        if (string_equals(action, transforms[i].name)) {
            return transforms[i].function(source);
        }
    }
    return empty_image;
}

static struct image rotate_cw90 (const struct image source) {
    size_t width = (source.size).x;
    size_t height = (source.size).y;

    struct image new_image = image_create((struct dimensions) {height, width});

    for (size_t i=0; i < height; i++) {
        for (size_t j=0; j<width; j++) {
            // (i,j) -> (w-1-j, i)
            new_image.data[(width-1-j)*height+i] = source.data[i*width+j];

        }
    }
    return new_image;
}

static struct image rotate_ccw90 (const struct image source) {
    size_t width = (source.size).x;
    size_t height = (source.size).y;

    struct image new_image = image_create((struct dimensions) {height, width});

    for (size_t i=0; i < height; i++) {
        for (size_t j=0; j<width; j++) {
            // (i,j) -> (j, h-1-i)
            new_image.data[j*height+(height-1-i)] = source.data[i*width+j];
        }
    }
    return new_image;
}

static struct image flip_vertical (const struct image source) {
    size_t width = (source.size).x;
    size_t height = (source.size).y;

    struct image new_image = image_create((struct dimensions) {width, height});

    for (size_t i=0; i < height; i++) {
        for (size_t j=0; j<width; j++) {
            // (i,j) -> (h-1-i, j)
            new_image.data[(height-1-i)*width+j] = source.data[i*width+j];
        }
    }
    return new_image;
}


static struct image flip_horizontal (const struct image source) {
    size_t width = (source.size).x;
    size_t height = (source.size).y;

    struct image new_image = image_create((struct dimensions) {width, height});

    for (size_t i=0; i < height; i++) {
        for (size_t j=0; j<width; j++) {
            // (i,j) -> (i, w-1-j)
            new_image.data[i*width+width-1-j] = source.data[i*width+j];

        }
    }
    return new_image;
}

static struct image none(const struct image source) {
    size_t width = (source.size).x;
    size_t height = (source.size).y;

    struct image new_image = image_create((struct dimensions) {width, height});
    for (size_t i=0; i < height; i++) {
        for (size_t j=0; j<width; j++) {
            // (i,j) -> (i,j)
            new_image.data[i*width+j] = source.data[i*width+j];
        }
    }
    return new_image;
}


