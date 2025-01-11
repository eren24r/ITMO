#include "bmp.h"
#include "image.h"

#include <errno.h>
#include <stdint.h>
#include <stdio.h>


int main( int argc, char* argv[]) {
	if (argc != 4) {
        fprintf(stderr, "Correct format to run the program: ./image-transform <source-image> <transformed-image> <tranformation>\n");
        return EINVAL;
	}

    const char* source_image = argv[1];
	const char* output_image = argv[2];
	const char* transformation = argv[3];

    FILE* in = open_file(source_image, "rb");

    if (in == NULL) {
        fprintf(stderr, "Failed to open source file\n");
        return ENOENT;
    }

    struct image image;
    enum read_status read_status = from_bmp(in, &image);

    if (!close_file(in)) {

        fprintf(stderr, "Warning: Failed to close source file\n");
    }

    switch (read_status){
        case READ_INVALID_COUNT:
            fprintf(stderr, "Failed to read BMP file\n");
            return ENOMEM;
        case READ_INVALID_HEADER:
            fprintf(stderr, "Invalid BITMPAFILEHEADER in the input BMP file\n");
            return ENOMEM;
        case READ_INVALID_SIGNATURE:
            fprintf(stderr, "Invalid BITMAPINFO in the input BMP file\n");
            return ENOMEM;
        case READ_INVALID_BITS:
            fprintf(stderr, "Failed to read pixels in the input BMP file\n");
            return ENOMEM;
        case READ_OK:
            break;
    }

;   struct image transformed = transform(image, transformation);

    if (image_equals(transformed, empty_image)) {
        image_destroy(&image);
        fprintf(stderr, "Invalid transformation\n");
        return EPERM;
    }

    FILE* out = open_file(output_image, "wb");

    if (!out) {
        image_destroy(&image);
        image_destroy(&transformed);
        fprintf(stderr, "Failed to open output file\n");

        return EIO;
    }

    enum write_status write_status = to_bmp(out, &transformed);
    image_destroy(&image);
    image_destroy(&transformed);

    switch (write_status){
        case WRITE_HEADER_ERROR:
            fprintf(stderr, "Failed to write BMP header to output file\n");
            return EIO;
        case WRITE_BITS_ERROR:
            fprintf(stderr, "Failed to write pixels to output file\n");
            return EIO;
        case WRITE_PADDING_ERROR:
            fprintf(stderr, "Failed to write padding to output file\n");
            return EIO;
        case WRITE_OK:
            break;
    }

    if (!close_file(out)) {
        fprintf(stderr, "Warning: Failed to close output file\n");
    }

	return 0;
}

