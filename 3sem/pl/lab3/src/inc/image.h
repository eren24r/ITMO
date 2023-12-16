//
// Created by eren on 12/16/23.
//

#ifndef LAB3_IMAGE_H
#define LAB3_IMAGE_H

#include <stdint.h>

struct pixel {
    uint8_t b, g, r;
};

struct image {
    uint64_t width, height;
    struct pixel *data;
};

struct image make_image(uint64_t width, uint64_t height);

struct image rotate(const struct image source);

struct image rotate_90(const struct image source);

struct image rotate_minus_90(const struct image source);

struct image rotate_180(const struct image source);

void empty_image(struct image *img);


#endif //LAB3_IMAGE_H
