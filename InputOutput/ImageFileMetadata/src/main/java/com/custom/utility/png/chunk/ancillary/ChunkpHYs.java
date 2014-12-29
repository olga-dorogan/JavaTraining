package com.custom.utility.png.chunk.ancillary;

import com.custom.utility.ByteConverter;

/**
 * Created by olga on 16.12.14.
 */
public class ChunkpHYs {
    public static void printInfo(byte[] data) {
        System.out.println("pHYs");
        /*
        Pixels per unit, X axis	4 bytes (PNG unsigned integer)
        Pixels per unit, Y axis	4 bytes (PNG unsigned integer)
        Unit specifier	        1 byte:
                                    0	unit is unknown
                                    1	unit is the metre
         */
        System.out.printf("Pixels per unit, X axis: %d\n", ByteConverter.convert(data, 0, (int) 0));
        System.out.printf("Pixels per unit, Y axis: %d\n", ByteConverter.convert(data, 4, (int) 0));
        if (data[8] == 0)
            System.out.println("Unit is unknown");
        else if (data[8] == 1)
            System.out.println("Unit is metre");
    }
}
