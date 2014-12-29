package com.custom.utility.bmp;

import com.custom.utility.ReverseByteOrder;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by olga on 10.12.14.
 */
public class BmpFormatParser {

    private DataInputStream dataInputStream;
    private BitmapFileHeader bitmapFileHeader;
    private BitmapInfoHeader bitmapInfoHeader;
    private boolean isLittleEndian = true;

    public BmpFormatParser(String fileAddress) throws FileNotFoundException {
        dataInputStream = new DataInputStream(new FileInputStream(fileAddress));
        try {
            bitmapFileHeader = new BitmapFileHeader(dataInputStream);
            bitmapInfoHeader = new BitmapInfoHeader((dataInputStream));
        } catch (IOException e) {
            System.out.println("Error with file processing.\n");
            return;
        } finally {
            try {
                dataInputStream.close();
            } catch (IOException e) {
            } finally {
                dataInputStream = null;
            }
        }
    }
    public void printMetadata() {
        if(bitmapInfoHeader==null || bitmapFileHeader==null)
            return;
        bitmapFileHeader.printFullInfo();
        bitmapInfoHeader.printFullInfo();
    }

    // almost all fields are unsigned values,
    // so their types should be by an order greater
    // (short -> int, int -> long, long -> float)
    private class BitmapFileHeader {
        private int bfType;
        private long bfSize;
        private int bfReserved1;
        private int bfReserved2;
        private long bfOffBits;

        public BitmapFileHeader(DataInputStream inputStream) throws IOException {
            bfType = inputStream.readUnsignedShort();
            // bfType must be equal
            // 0x424D (for little-endian byte order) or
            // 0x4D42 (for big-endian byte order)
            if ((((bfType >> 8) & 0xFF) == 0x4D) && ((bfType & 0xFF) == 0x42))
                isLittleEndian = false;
            else if ((((bfType >> 8) & 0xFF) == 0x42) && ((bfType & 0xFF) == 0x4D))
                isLittleEndian = true;
            else
                throw new IOException("Illegal file format.");

            // unsigned int can be read as combination of two unsigned shorts
            bfSize = (inputStream.readUnsignedShort() << 16) + inputStream.readUnsignedShort();
            bfReserved1 = inputStream.readUnsignedShort();
            bfReserved2 = inputStream.readUnsignedShort();
            bfOffBits = (inputStream.readUnsignedShort() << 16) + inputStream.readUnsignedShort();
        }

        public void printFullInfo() {
            System.out.printf("%s \n", getClass().getSimpleName());
            System.out.printf("Byte 1,2 should be BM [0x4D 0x42]: %x\n", bfType);
            System.out.printf("Bytes 3-6 (file size in bytes): %d\n", checkByteOrder((int) bfSize));
            System.out.printf("Bytes 7,8; 9,10 (are reserved and must be zero): %d;%d\n",
                    checkByteOrder((short) bfReserved1), checkByteOrder((short) bfReserved2));
            System.out.printf("Bytes 11-14 (image data offset in bytes): %d\n", checkByteOrder((int) bfOffBits));
        }
    }

    private class BitmapInfoHeader {
        private final Map<Integer, String> compressionTypes;

        {
            compressionTypes = new HashMap<Integer, String>(10);
            compressionTypes.put(0x0000, "BI_RGB (uncompressed RGB format)");
            compressionTypes.put(0x0001, "BI_RLE8");
            compressionTypes.put(0x0002, "BI_RLE4");
            compressionTypes.put(0x0003, "BI_BITFIELDS");
            compressionTypes.put(0x0004, "BI_JPEG");
            compressionTypes.put(0x0005, "BI_PNG");
            compressionTypes.put(0x000B, "BI_CMYK");
            compressionTypes.put(0x000C, "BI_CMYKRLE8");
            compressionTypes.put(0x000D, "BI_CMYKRLE4");
        }

        private long biSize;
        //signed field (the type is a real type)
        private int biWidth;
        //signed field (the type is a real type)
        private int biHeight;
        private int biPlanes;
        private int biBitCount;
        private long biCompression;
        private long biSizeImage;
        //signed field (the type is a real type)
        private int biXPelsPerMeter;
        //signed field (the type is a real type)
        private int biYPelsPerMeter;
        private long biClrUsed;
        private long biClrImportant;

        public BitmapInfoHeader(DataInputStream inputStream) throws IOException {
            biSize = (inputStream.readUnsignedShort() << 16) + inputStream.readUnsignedShort();
            biWidth = inputStream.readInt();
            biHeight = inputStream.readInt();
            biPlanes = inputStream.readUnsignedShort();
            biBitCount = inputStream.readUnsignedShort();
            biCompression = (inputStream.readUnsignedShort() << 16) + inputStream.readUnsignedShort();
            biSizeImage = (inputStream.readUnsignedShort() << 16) + inputStream.readUnsignedShort();
            biXPelsPerMeter = inputStream.readInt();
            biYPelsPerMeter = inputStream.readInt();
            biClrUsed = (inputStream.readUnsignedShort() << 16) + inputStream.readUnsignedShort();
            biClrImportant = (inputStream.readUnsignedShort() << 16) + inputStream.readUnsignedShort();
        }

        public void printFullInfo() {
            System.out.printf("%s\n", getClass().getSimpleName());
            System.out.printf("Bytes 15-18 (size of BitmapInfoHeader block in bytes, must be 40): %d\n",
                    checkByteOrder((int) biSize));
            System.out.printf("Bytes 19-22 (image width in pixels):  %d\n", checkByteOrder(biWidth));
            System.out.printf("Bytes 23-26 (image height in pixels): %d\n", checkByteOrder(biHeight));
            System.out.printf("Bytes 27,28 (number of planes in the image, must be 1): %d\n",
                    checkByteOrder((short) biPlanes));
            System.out.printf("Bytes 29,30 (number of bits per pixel (1, 4, 8, 16, 24 or 32)): %d\n",
                    checkByteOrder((short) biBitCount));
            System.out.printf("Bytes 31-34 (compression type): %s\n",
                    compressionTypes.get(checkByteOrder((int) biCompression)));
            System.out.printf("Bytes 35-38 (size of image in bytes (if it is compressed can be be zero)): %d\n",
                    checkByteOrder((int) biSizeImage));
            System.out.printf("Bytes 39-42 (horizontal resolution of the target device for the bitmap " +
                    "in pixels per meter): %d\n", checkByteOrder(biXPelsPerMeter));
            System.out.printf("Bytes 43-46 (vertical resolution of the target device for the bitmap " +
                    "in pixels per meter): %d\n", checkByteOrder(biYPelsPerMeter));
            // biClrUsed is used to identify number of colors used by the bitmap
            // if number of bits per pixel is 16
            // (there are two variants: 2^16 or 2^15 in this case)
            System.out.printf("Bytes 47-50 (the number of color that are actually used by the bitmap " +
                            "if the number of bits per pixel is equal 16, zero otherwise): %d\n",
                    checkByteOrder((int) biClrUsed));
            System.out.printf("Bytes 51-54 (the number of color are considered important for displaying " +
                    "the bitmap, zero if all colors are important): %d\n", checkByteOrder((int) biClrImportant));
        }
    }

    private short checkByteOrder(short data) {
        if (isLittleEndian)
            return ReverseByteOrder.reverse(data);
        return data;
    }

    private int checkByteOrder(int data) {
        if (isLittleEndian)
            return ReverseByteOrder.reverse(data);
        return data;
    }

}
