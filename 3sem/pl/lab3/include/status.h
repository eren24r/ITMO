//
// Created by eren on 12/16/23.
//

#ifndef IMAGE_TRANSFORMER_STATUS_H
#define IMAGE_TRANSFORMER_STATUS_H

#include "image.h"
#include <stdio.h>

enum read_status {
    READ_OK = 0,
    READ_INVALID_SIGNATURE,
    READ_INVALID_BITS,
    READ_INVALID_HEADER
};

enum write_status {
    WRITE_OK = 0,
    WRITE_ERROR
};

enum read_status from_bmp(FILE *in, struct image *img);
enum write_status to_bmp(FILE *out, struct image const *img);

#endif //IMAGE_TRANSFORMER_STATUS_H
