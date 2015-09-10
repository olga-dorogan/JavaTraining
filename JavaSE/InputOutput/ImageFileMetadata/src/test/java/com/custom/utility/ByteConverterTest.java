package com.custom.utility;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by olga on 11.12.14.
 */
public class ByteConverterTest {
    @Test
    public void testConvertToInt() throws Exception {
        byte[] testData = new byte[]{(byte)0xAA, (byte)0xBB, (byte)0xCC, (byte)0xDD};
        int temp = ByteConverter.convert(testData, 0, 0);
        assertEquals(0xAABBCCDD, temp);
    }

    @Test
    public void testConvertToShort() throws Exception {
        byte[] testData = new byte[]{(byte)0xAA, (byte)0xBB};
        short temp = ByteConverter.convert(testData, 0, (short) 0);
        assertEquals((short)0xAABB, temp);
    }
}
