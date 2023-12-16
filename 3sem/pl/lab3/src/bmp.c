#include <stdlib.h>
#include "inc/status.h"

#pragma pack(push, 1)

struct bmp_header {
    uint16_t bfType;
    uint32_t bfileSize;
    uint32_t bfReserved;
    uint32_t bOffBits;
    uint32_t biSize;
    uint32_t biWidth;
    uint32_t biHeight;
    uint16_t biPlanes;
    uint16_t biBitCount;
    uint32_t biCompression;
    uint32_t biSizeImage;
    uint32_t biXPelsPerMeter;
    uint32_t biYPelsPerMeter;
    uint32_t biClrUsed;
    uint32_t biClrImportant;
};

#pragma pack(pop)

enum read_status from_bmp(FILE *in, struct image *img) {

    struct bmp_header header;
    fread(&header, sizeof(struct bmp_header), 1, in);

    if (header.bfType != 0x4D42) {
        return READ_INVALID_SIGNATURE;
    }

    img->width = header.biWidth;
    img->height = header.biHeight;

    img->data = (struct pixel *)malloc(img->width * img->height * sizeof(struct pixel));

    fseek(in, header.bOffBits, SEEK_SET);

    size_t padding = (4 - (img->width * sizeof(struct pixel)) % 4) % 4;

    for (size_t i = 0; i < img->height; ++i) {
        for (size_t j = 0; j < img->width; ++j) {
            fread(&(img->data[i * img->width + j]), sizeof(struct pixel), 1, in);
        }

        fseek(in, padding, SEEK_CUR);
    }

    return READ_OK;
}

enum write_status to_bmp(FILE *out, const struct image *img) {
    struct bmp_header header = {
            .bfType = 0x4D42,
            .bfileSize = sizeof(struct bmp_header) + img->width * img->height * sizeof(struct pixel),
            .bfReserved = 0,
            .bOffBits = sizeof(struct bmp_header),
            .biSize = 40,
            .biWidth = img->width,
            .biHeight = img->height,
            .biPlanes = 1,
            .biBitCount = 24,
            .biCompression = 0,
            .biSizeImage = img->width * img->height * sizeof(struct pixel),
            .biXPelsPerMeter = 0,
            .biYPelsPerMeter = 0,
            .biClrUsed = 0,
            .biClrImportant = 0
    };

    fwrite(&header, sizeof(struct bmp_header), 1, out);

    size_t padding = (4 - (img->width * sizeof(struct pixel)) % 4) % 4;

    for (size_t i = 0; i < img->height; ++i) {
        for (size_t j = 0; j < img->width; ++j) {
            fwrite(&(img->data[i * img->width + j]), sizeof(struct pixel), 1, out);
        }

        uint8_t zero = 0;
        for (size_t k = 0; k < padding; ++k) {
            fwrite(&zero, sizeof(uint8_t), 1, out);
        }
    }

    return WRITE_OK;
}
