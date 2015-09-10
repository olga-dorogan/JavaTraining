package com.custom.utility;

/**
 * Created by olga on 11.12.14.
 */
public class ReverseByteOrder {
    public static short reverse(short data) {
        byte lowByte = (byte) (data);
        byte highByte = (byte) (data >> 8);
        return (short) ((lowByte << 8) | (highByte & 0xFF));
    }

    public static int reverse(int data) {
        short lowShort = reverse((short) data);
        short highShort = reverse((short) (data >> 16));
        return ((int) (lowShort) << 16) | (highShort & 0xFFFF);
    }

    public static long reverse(long data) {
        int lowInt = reverse((int) data);
        int highInt = reverse((int) (data >> 32));
        return ((long) (lowInt) << 32) | (highInt & 0xFFFFFFFF);
    }
}
