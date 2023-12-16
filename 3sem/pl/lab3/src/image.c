#include "inc/image.h"
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

struct image rotate(const struct image source) {
    struct image rotated = make_image(source.height, source.width);

    for (uint64_t i = 0; i < source.height; ++i) {
        for (uint64_t j = 0; j < source.width; ++j) {
            rotated.data[j * source.height + source.height - i - 1] = source.data[i * source.width + j];
        }
    }

    return rotated;
}

struct image rotate_90(const struct image source) {
    return rotate(source);
}

struct image rotate_minus_90(const struct image source) {
    struct image rotated = make_image(source.height, source.width);

    for (uint64_t i = 0; i < source.height; ++i) {
        for (uint64_t j = 0; j < source.width; ++j) {
            rotated.data[(source.width - j - 1) * source.height + i] = source.data[i * source.width + j];
        }
    }

    return rotated;
}

struct image rotate_180(const struct image source) {
    struct image rotated = make_image(source.width, source.height);

    for (uint64_t i = 0; i < source.height; ++i) {
        for (uint64_t j = 0; j < source.width; ++j) {
            rotated.data[(source.height - i - 1) * source.width + (source.width - j - 1)] = source.data[i * source.width + j];
        }
    }

    return rotated;
}
