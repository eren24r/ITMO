  #ifndef BMP_H
  #define BMP_H

  #include "image.h"
  #include <stdio.h>


  enum read_status  {
    READ_OK = 0,
    READ_INVALID_SIGNATURE,
    READ_INVALID_BITS,
    READ_INVALID_HEADER,
    READ_INVALID_COUNT
  };
  enum  write_status  {
    WRITE_OK = 0,
    WRITE_HEADER_ERROR,
    WRITE_BITS_ERROR,
    WRITE_PADDING_ERROR
  };

  enum read_status from_bmp( FILE* in, struct image* image );
  enum write_status to_bmp( FILE* out, const struct image* image );
  #endif // BMP_H
