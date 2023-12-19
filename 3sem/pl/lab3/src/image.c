#include "image.h"
#include "status.h"
#include <stdlib.h>

struct image make_image(uint64_t width, uint64_t height) {
    struct image img;
    img.width = width;
    img.height = height;
    img.data = (struct pixel *)malloc(width * height * sizeof(struct pixel));

    return img;
}

void empty_image(struct image *img) {
    free(img->data);
    img->data = NULL;
    img->width = 0;
    img->height = 0;
}

struct image rotate_90(struct image const source) {
    struct image rotated = make_image(source.height, source.width);
    if (rotated.data == NULL) {
        return rotated;
    }

    for (uint64_t y = 0; y < source.height; y++) {
        for (uint64_t x = 0; x < source.width; x++) {
            rotated.data[y + (source.width - x - 1) * rotated.width] = source.data[x + y * source.width];
        }
    }

    return rotated;
}

struct image getImage(const struct image* original){
    struct image duplicate;
    duplicate.width = original->width;
    duplicate.height = original->height;

    duplicate.data = (struct pixel*)malloc(original->width * original->height * sizeof(struct pixel));
    if (!duplicate.data) {
        fprintf(stderr, "Error allocating memory for duplicate image\n");
        exit(EXIT_FAILURE);
    }

    for (uint64_t i = 0; i < original->width * original->height; ++i) {
        duplicate.data[i] = original->data[i];
    }

    return duplicate;
}

struct image rotate_180(struct image const source) {
    struct image rotated = make_image(source.width, source.height);
    if (rotated.data == NULL) {
        return rotated;
    }

    for (uint64_t y = 0; y < source.height; y++) {
        for (uint64_t x = 0; x < source.width; x++) {
            rotated.data[(rotated.width - x - 1) + (rotated.height - y - 1) * rotated.width] = source.data[x + y * source.width];
        }
    }

    return rotated;
}

struct image rotate_270(struct image const source) {
    struct image rotated = make_image(source.height, source.width);
    if (rotated.data == NULL) {
        return rotated;
    }

    for (uint64_t y = 0; y < source.height; y++) {
        for (uint64_t x = 0; x < source.width; x++) {
            rotated.data[(rotated.width - y - 1) + x * rotated.width] = source.data[x + y * source.width];
        }
    }

    return rotated;
}
