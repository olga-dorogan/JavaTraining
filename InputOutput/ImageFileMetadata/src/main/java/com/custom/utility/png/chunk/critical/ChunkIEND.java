package com.custom.utility.png.chunk.critical;

/**
 * Created by olga on 16.12.14.
 */
public class ChunkIEND {
    public static void printInfo(byte[] data){
        System.out.println("IEND");
        System.out.printf("Data length in the chunk (must be 0): %d\n", data.length);
    }
}
