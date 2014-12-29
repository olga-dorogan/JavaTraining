package com.custom.utility;

/**
 * Created by olga on 11.12.14.
 */
public class ByteConverter {
    public static int convert(byte[] data, int offset, int temp) {
        temp = (((int) data[offset] << 24) & (0xFF << 24)) |
                (((int) data[offset + 1] << 16) & (0xFF << 16)) |
                (((int) data[offset + 2] << 8) & (0xFF << 8)) |
                data[offset + 3] & 0xFF;
        return temp;
    }
    public static short convert(byte[] data, int offset, short temp){
        temp = (short)
                ((((short) data[offset] << 8) & (0xFF << 8)) |
                (short)(data[offset + 1] & 0xFF));
        return temp;
    }
}
