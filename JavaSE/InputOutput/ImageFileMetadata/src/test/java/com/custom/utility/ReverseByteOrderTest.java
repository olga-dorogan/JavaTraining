package com.custom.utility;

import com.custom.utility.ReverseByteOrder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by olga on 11.12.14.
 */
public class ReverseByteOrderTest {
    @Test
    public void testReverseShort() throws Exception {
        short testData = (short) 0xAABB;
        short resultData = ReverseByteOrder.reverse(testData);
        assertEquals((byte) (testData >> 8), (byte) resultData);
        assertEquals((byte) testData, (byte) (resultData >> 8));
    }

    @Test
    public void testReverseInt() throws Exception {
        int testData = 0xAABBCCDD;
        int resultData = ReverseByteOrder.reverse(testData);
        assertEquals((byte) (testData >> 24), (byte) resultData);
        assertEquals((byte) (testData >> 16), (byte) (resultData >> 8));
        assertEquals((byte) (testData >> 8), (byte) (resultData >> 16));
        assertEquals((byte) testData, (byte) (resultData >> 24));
    }
    @Test
    public void testReverseLong() throws Exception {
        long testData = 0x1122334455667788L;
        long resultData = ReverseByteOrder.reverse(testData);
        assertEquals((byte) (testData >> 56), (byte) resultData);
        assertEquals((byte) (testData >> 48), (byte) (resultData >> 8));
        assertEquals((byte) (testData >> 40), (byte) (resultData >> 16));
        assertEquals((byte) (testData >> 32), (byte) (resultData >> 24));
        assertEquals((byte) (testData >> 24), (byte) (resultData >> 32));
        assertEquals((byte) (testData >> 16), (byte) (resultData >> 40));
        assertEquals((byte) (testData >> 8), (byte) (resultData >> 48));
        assertEquals((byte) testData, (byte) (resultData >> 56));
    }
}
