package com.custom.utility.png.chunk.critical;

/**
 * Created by olga on 11.12.14.
 */
public class ChunkPLTE {
    public static void printInfo(byte[] data){
        System.out.println("PLTE");
        System.out.println("If the color type is indexed-color palette must contain list of color");
        System.out.printf("For the image list of color contains %d colors\n", data.length/3);
    }
}
