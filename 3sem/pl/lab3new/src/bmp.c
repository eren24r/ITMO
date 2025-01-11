#include "bmp.h"

#include "image.h"
#include <inttypes.h>
#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>

#define BM 0x4D42



struct __attribute__((packed)) bmp_header {
    uint16_t bfType;
    uint32_t bfileSize;
    uint16_t bfReserved1;
	uint16_t bfReserved2;
    uint32_t bfOffBits;
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

static bool write_bmp_header(FILE* out, const struct bmp_header header);
static bool write_row_of_bits(FILE* out, const struct pixel* data, size_t width, size_t i) ;
static bool write_padding(FILE* out, uint32_t padding);
static bool check_file_header(FILE* in, const struct bmp_header header);
static bool check_info_header(const struct bmp_header header);
static struct bmp_header generate_bmp_header(const struct image* image);

enum read_status from_bmp( FILE* in, struct image* image ) {

	struct bmp_header bmp_header;

    if (fread(&bmp_header, sizeof(bmp_header), 1, in) != 1) {
		return READ_INVALID_COUNT;
	}

    if (!check_file_header(in, bmp_header)) {
		return READ_INVALID_HEADER;
	}

    if (!check_info_header(bmp_header)) {
		return READ_INVALID_SIGNATURE;
	}

	uint64_t width = bmp_header.biWidth;
	uint64_t height = bmp_header.biHeight;

	*image = image_create((struct dimensions) {width, height});
	uint32_t padding = calculate_padding(width, bmp_header.biBitCount);

	for (uint64_t i=0; i < height; i++) {
        if (fread((image -> data)+i*width, sizeof(struct pixel), width, in + bmp_header.bfOffBits - sizeof(struct bmp_header)) != width) {
            image_destroy(image);
            return READ_INVALID_BITS;
        }

	    fseek(in, padding, SEEK_CUR);
	}

    return READ_OK;
}

enum write_status to_bmp( FILE* out, const struct image* image ) {

    size_t width = (image -> size).x;
    size_t height = (image -> size).y;
    uint32_t padding = calculate_padding(width, 24);

    struct bmp_header header = generate_bmp_header(image);

    if (!write_bmp_header(out, header)) {
        return WRITE_HEADER_ERROR;
    }

    for (size_t i=0; i < height; i++) {

        if (!write_row_of_bits(out, (image -> data), width, i)) {
            return WRITE_BITS_ERROR;
        }

        if (!write_padding(out, padding)) {
            return WRITE_PADDING_ERROR;
        }
    }

    return WRITE_OK;
}

// Создаёт корректный заголовок BMP файла
static struct bmp_header generate_bmp_header(const struct image* image) {
        size_t width = (image -> size).x;
        size_t height = (image -> size).y;
        uint32_t padding = calculate_padding(width, 24);
        struct bmp_header header = {
        // Так как формат BMP, то bfType должен содержать 'BM' = 0x4D42
        .bfType = BM,
        // Размер выходного файла вычисляется, как размер заголовка + размер самого изображения(
        .bfileSize = sizeof(struct bmp_header) + (sizeof(struct pixel) * width + padding)* height,
        // Зарезервированы, должны содержать 0
        .bfReserved1 = 0,
        .bfReserved2 = 0,
        // Изображение находится сразу за заголовком
        .bfOffBits = sizeof(struct bmp_header),
        // Размер BITMAPINFO в байтах
        .biSize = 40,
        .biWidth = width,
        .biHeight = height,
        // Так как формат BMP, то biPlanes всегда 1
        .biPlanes = 1,
        // Так как мы храним RGB пиксели, то 24 бита
        .biBitCount = 24,
        // Так как формат BMP, то всегда 0(BI_RGB)
        .biCompression = 0,
        // Так как формат хранения "двумерный массив"( на практике одномерный), то это поле может быть 0
        .biSizeImage = 0,
        // Используются для выбора разрешения отображения для конкретного монитора(оставил столько же, сколько было в изначальном файле)
        .biXPelsPerMeter = 2835,
        .biYPelsPerMeter = 2835,
        // Используем все цвета
        .biClrUsed = 0,
        // Все цвета нужны для отображения изображения
        .biClrImportant = 0
    };
    return header;
}

// Записывает в файл bmp_header
static bool write_bmp_header(FILE* out, const struct bmp_header header) {

    return fwrite(&header, sizeof(struct bmp_header), 1, out) == 1;
}
// Записывает в файл пиксели изображения
static bool write_row_of_bits(FILE* out, const struct pixel* data, size_t width, size_t i) {

    return fwrite(data + i*width, sizeof(struct pixel), width, out) == width;
}
// Записывает в файл padding, чтобы размер был кратен 4
static bool write_padding(FILE* out, uint32_t padding) {

    uint8_t padding_array[3] = {0,0,0};

    return fwrite(padding_array, 1, padding, out) == padding;
}

static bool check_file_header(FILE* in, const struct bmp_header header) {

    return header.bfType == BM &&
		header.bfReserved1 == 0 &&
		header.bfReserved2 == 0 &&
		header.bfileSize == get_file_size(in);
}

static bool check_info_header(const struct bmp_header header) {

    return header.biPlanes == 1 &&
		header.biBitCount == 24 &&
		header.biCompression == 0;
}

