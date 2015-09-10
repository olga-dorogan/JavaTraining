package com.custom.utility.png;

import com.custom.utility.png.chunk.Chunk;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by olga on 11.12.14.
 */
public class PngFormatParser {

    private DataInputStream dataInputStream;
    private static final byte[] PNG_SIGNATURE = new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};

    public PngFormatParser(String fileAddress) throws FileNotFoundException {
        dataInputStream = new DataInputStream(new FileInputStream(fileAddress));
        try {
            for (int i = 0; i < PNG_SIGNATURE.length; i++) {
                if (dataInputStream.readByte() != PNG_SIGNATURE[i]) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                    }
                    dataInputStream = null;
                    System.out.println("Unsupported file format");
                    return;
                }
            }
        } catch (IOException e) {
            try {
                dataInputStream.close();
            } catch (IOException exc) {
            }
            dataInputStream = null;
            System.out.println("Unexpected IOException.");
        }
    }

    public void printMetadata() {
        if (dataInputStream == null)
            return;
        try {
            Chunk chunk;
            do {
                chunk = new Chunk(dataInputStream);
                chunk.printInfo();
            }
            while (!chunk.isLast());
        } catch (IOException e) {
            System.out.println("Unexpected IOException.");
            e.printStackTrace();
        } finally {
            try {
                dataInputStream.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }

}
