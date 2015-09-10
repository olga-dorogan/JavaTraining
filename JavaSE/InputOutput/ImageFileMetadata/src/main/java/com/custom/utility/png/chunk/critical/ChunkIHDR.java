package com.custom.utility.png.chunk.critical;

import com.custom.utility.ByteConverter;

/**
 * Created by olga on 11.12.14.
 */
public class ChunkIHDR {
    public static void printInfo(byte[] data) {
        System.out.println("IHDR");
        /*
        Width	            4 bytes
        Height	            4 bytes
        Bit depth	        1 byte
        Colour type	        1 byte
        Compression method	1 byte
        Filter method	    1 byte
        Interlace method	1 byte
         */
        int currentOffset = 0;
        System.out.printf("Width: %d\n", ByteConverter.convert(data, currentOffset, 0));
        currentOffset += 4;
        System.out.printf("Height: %d\n", ByteConverter.convert(data, currentOffset, 0));
        currentOffset += 4;
        System.out.printf("Bit depth: %d\n", data[currentOffset]);
        currentOffset++;
        /*
            Greyscale	0		Each pixel is a greyscale sample
            Truecolour	2	    Each pixel is an R,G,B triple
            Indexed-colour	3	Each pixel is a palette index; a PLTE chunk shall appear.
            Greyscale with alpha	4	Each pixel is a greyscale sample followed by an alpha sample.
            Truecolour with alpha	6
        */
        System.out.print("Color type: ");
        switch (data[currentOffset]) {
            case 0:
                System.out.println("Greyscale");
                break;
            case 2:
                System.out.println("Truecolor");
                break;
            case 3:
                System.out.println("Indexed-color");
                break;
            case 4:
                System.out.println("Greyscale with alpha");
                break;
            case 6:
                System.out.println("Truecolor with alpha");
                break;
        }
        currentOffset++;
        System.out.printf("Compression method (must be 0): %d\n", data[currentOffset]);
        currentOffset++;
        System.out.printf("Filter method (must be 0): %d\n", data[currentOffset]);
        currentOffset++;
        System.out.printf("Interlace method (0 - no interlace, 1 - Ada7 interlace): %d\n",
                data[currentOffset]);
        currentOffset++;
    }
}
