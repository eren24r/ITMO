#ifndef IMAGE_H
#define IMAGE_H

#include <inttypes.h>
#include <stdbool.h>

#include <stdio.h>

#include <stddef.h>
#include <stdint.h>
#include <stdlib.h>

struct dimensions {
	size_t x;
	size_t y;
};

struct pixel {
	uint8_t b,g,r;
};

struct image {
	struct dimensions size;
	struct pixel* data;
};

extern struct image empty_image;

struct image image_create( struct dimensions size );
void image_destroy( struct image* image );
bool image_equals( const struct image a, const struct image b );

uint32_t calculate_padding(uint32_t width, uint16_t bitCount);
FILE* open_file(const char* file_path, const char* mode);
bool close_file(FILE* file);
long get_file_size(FILE* file);
bool string_equals(const char* a, const char* b);

struct image transform(struct image const source, const char* action);

#endif // IMAGE_H
