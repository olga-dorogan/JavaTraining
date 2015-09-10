package com.custom;

import com.custom.utility.bmp.BmpFormatParser;
import com.custom.utility.png.PngFormatParser;

import java.io.FileNotFoundException;

/**
 * Created by olga on 10.12.14.
 */
public class MainEntry {
    private static final String BMP_ADDRESS = "sun.bmp";
    private static final String PNG_ADDRESS = "sun.png";

    public static void main(String[] args) {
        System.out.println("###### Bmp file");
        getInfoAboutBmpFile(BMP_ADDRESS);
        System.out.println("###### Png file");
        getInfoAboutPngFile(PNG_ADDRESS);
    }

    private static void getInfoAboutBmpFile(String fileAddress) {
        try {
            BmpFormatParser bmpFormatParser = new BmpFormatParser(fileAddress);
            bmpFormatParser.printMetadata();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }
    }

    private static void getInfoAboutPngFile(String fileAddress) {
        try {
            PngFormatParser pngFormatParser = new PngFormatParser(fileAddress);
            pngFormatParser.printMetadata();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }
    }
}
