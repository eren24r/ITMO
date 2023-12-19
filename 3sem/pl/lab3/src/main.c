#include "image.h"
#include "status.h"
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[4]) {
    if (argc != 4) {
        fprintf(stderr, "Usage: %s <source-image> <transformed-image> <angle>\n", argv[0]);
        return EXIT_FAILURE;
    }

    FILE *input_file = fopen(argv[1], "rb");
    if (!input_file) {
        perror("Error opening source image");
        return EXIT_FAILURE;
    }

    struct image original_image;
    enum read_status read_result = from_bmp(input_file, &original_image);
    fclose(input_file);

    if (read_result != READ_OK) {
        fprintf(stderr, "Error reading source image: %d\n", read_result);
        return EXIT_FAILURE;
    }

    int angle = atoi(argv[3]);
    struct image transformed_image;

    switch (angle) {
        case 0: {
            /*struct image temp_img = rotate_180(original_image);
            transformed_image = rotate_180(temp_img);
            free(temp_img.data);*/
            transformed_image = getImage(&original_image);;
            break;
        }
        case 90:
        case -270:
            transformed_image = rotate_90(original_image);
            break;
        case 180:
        case -180:
            transformed_image = rotate_180(original_image);
            break;
        case 270:
        case -90:
            transformed_image = rotate_270(original_image);
            break;

        default:
            fprintf(stderr, "angle will be: 90, -90, 180, -180, 270, -270\n");
            empty_image(&original_image);
            return EXIT_FAILURE;
    }

    FILE *output_file = fopen(argv[2], "wb");
    if (!output_file) {
        perror("Error opening transformed image");
        empty_image(&original_image);
        empty_image(&transformed_image);
        return EXIT_FAILURE;
    }

    enum write_status write_result = to_bmp(output_file, &transformed_image);
    fclose(output_file);

    empty_image(&original_image);
    empty_image(&transformed_image);

    if (write_result != WRITE_OK) {
        fprintf(stderr, "Error writing transformed image: %d\n", write_result);
        return EXIT_FAILURE;
    }

    printf("Transformation completed successfully.\n");

    return EXIT_SUCCESS;
}
