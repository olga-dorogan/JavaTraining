package com.custom.utility.png.chunk.ancillary;

import com.custom.utility.ByteConverter;

/**
 * Created by olga on 16.12.14.
 */
public class ChunkbKGD {
    public static void printInfo(byte[] data) {
        System.out.println("bKGND");
        /*
            Colour types 0 and 4
                Greyscale	    2 bytes
            Colour types 2 and 6
                Red	            2 bytes
                Green	        2 bytes
                Blue	        2 bytes
            Colour type 3
                Palette index	1 byte
         */
        switch (data.length) {
            case 1:
                System.out.printf("Palette index (color type should be indexed-colour: %d\n",
                        data[0]);
                break;
            case 2:
                System.out.printf("Greyscale (color type should be 0 or 4 " +
                                "(greyscale or greyscale with alpha): %d\n",
                        (short) ByteConverter.convert(data, 0, (short) 0));
                break;
            case 6:
                System.out.printf("RGB (color type should be Truecolour or Truecolour with alpha): %x, %x, %x",
                        (short) ByteConverter.convert(data, 0, (short) 0),
                        (short) ByteConverter.convert(data, 2, (short) 0),
                        (short) ByteConverter.convert(data, 4, (short) 0));
                break;
        }
    }
}
