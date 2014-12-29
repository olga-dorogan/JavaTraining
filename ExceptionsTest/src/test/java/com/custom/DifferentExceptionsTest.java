package com.custom;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by olga on 05.12.14.
 */
public class DifferentExceptionsTest {

    @Test(expected = NullPointerException.class)
    public void testNullPointerException() {
        Object obj = null;
        obj.toString();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testArrayIndexOfBoundsException() {
        final int size = 1;
        Object[] arr = new Object[size];
        Object testObject = arr[size];
    }

    @Test(expected = ClassCastException.class)
    public void testClassCastException() {
        Object testIntegerAsObject = Integer.valueOf(5);
        String testString = (String) testIntegerAsObject;
    }

    @Test(expected = ClassNotFoundException.class)
    public void testClassNotFoundException() throws Exception {
        // must be full class name (java.lang.String)
        Class testClassForString = Class.forName("String");
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateException() {
        Iterator<Integer> emptyIterator = new ArrayList<Integer>().iterator();
        emptyIterator.remove();
    }

    @Test(expected = NumberFormatException.class)
    public void testNumberFormatException() {
        // parameter must be the String containing only decimal values and sign
        int testValue = Integer.parseInt("12.33");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupportedOperationException() throws Exception {
        Collection<Integer> unmodifiableCollection = Collections.unmodifiableCollection(new ArrayList<Integer>());
        unmodifiableCollection.add(5);
    }

    @Test (expected = OutOfMemoryError.class)
    public void testOutOfMemoryError() throws Exception {
        double[][] arr = new double[Integer.MAX_VALUE][Integer.MAX_VALUE];
    }

    @Test (expected = StackOverflowError.class)
    public void testStackOverflowError() throws Exception {
        customMethodForStackOverflow();
    }
    void customMethodForStackOverflow(){
        customMethodForStackOverflow();
    }
}
