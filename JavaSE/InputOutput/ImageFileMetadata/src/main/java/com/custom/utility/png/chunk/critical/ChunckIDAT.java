package com.custom.utility.png.chunk.critical;

/**
 * Created by olga on 16.12.14.
 */
public class ChunckIDAT {
    public static void printInfo(byte[] data){
        System.out.println("IDAT");
        System.out.printf("Data length in the chunk (bytes in the image): %d\n", data.length);
    }
}
