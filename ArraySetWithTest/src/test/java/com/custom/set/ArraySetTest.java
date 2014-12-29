package com.custom.set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ArraySetTest {
    Set<Integer> emptySet;
    Set<Integer> filledWithTestDataSet;

    static final int FILLED_SET_SIZE = 3;
    static final Integer[] testData = new Integer[FILLED_SET_SIZE];
    static final Integer testInteger = Integer.MAX_VALUE;

    static {
        for (int i = 0; i < FILLED_SET_SIZE; i++)
            testData[i] = i;
    }

    @Before
    public void initialize() {
        emptySet = new ArraySet<Integer>();
        filledWithTestDataSet = new ArraySet<Integer>();
        for (int i = 0; i < FILLED_SET_SIZE; i++)
            filledWithTestDataSet.add(testData[i]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArraySetConstructorWithIllegalInitialCapacity() {
        final int illegalInitialCapacity = -1;
        try {
            Set<Integer> set = new ArraySet<Integer>(-1);
        } catch (IndexOutOfBoundsException e) {
            String expectedMsg = "Illegal initial capacity: " + illegalInitialCapacity;
            assertEquals(expectedMsg, e.getMessage());
            throw e;
        }
    }

    @Test
    public void testArraySetConstructorWithZeroInitialCapacityShouldResizeCorrectly() {
        Set<Integer> set = new ArraySet<Integer>(0);
        assertTrue(set.add(1));
    }

    @Test
    public void testSize() {
        assertEquals(FILLED_SET_SIZE, filledWithTestDataSet.size());
    }

    @Test
    public void testSizeOnEmptySet() {
        assertEquals(0, emptySet.size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(filledWithTestDataSet.isEmpty());
    }

    @Test
    public void testIsEmptyOnEmptySet() {
        assertTrue(emptySet.isEmpty());
    }

    @Test
    public void testContains() {
        assertTrue(filledWithTestDataSet.contains(testData[0]));
    }

    @Test
    public void testContainsWhenNotContain() {
        assertFalse(filledWithTestDataSet.contains(testInteger));
    }

    @Test
    public void testContainsWithNull() {
        assertFalse(filledWithTestDataSet.contains(null));
    }

    @Test
    public void testContainsWithNullWhenContainNull() {
        filledWithTestDataSet.add(null);
        assertTrue(filledWithTestDataSet.contains(null));
    }

    @Test
    public void testContainsOnEmptySet() {
        assertFalse(emptySet.contains(testInteger));
    }

    @Test
    public void testAdd() {
        assertTrue(filledWithTestDataSet.add(testInteger));
    }

    @Test
    public void testAddNull() {
        assertTrue(filledWithTestDataSet.add(null));
    }

    @Test
    public void testAddNullWhenContainNull() {
        filledWithTestDataSet.add(null);
        assertFalse(filledWithTestDataSet.add(null));
    }

    @Test
    public void testAddWhenContainsShouldReturnFalse() {
        assertFalse(filledWithTestDataSet.add(testData[0]));
    }

    @Test
    public void testRemove() {
        assertTrue(filledWithTestDataSet.remove(testData[0]));
        assertEquals(FILLED_SET_SIZE - 1, filledWithTestDataSet.size());
        for (int i = 1; i < FILLED_SET_SIZE; i++)
            assertTrue(filledWithTestDataSet.contains(testData[i]));
    }

    @Test
    public void testRemoveWhenNotContain() {
        assertFalse(filledWithTestDataSet.remove(testInteger));
    }

    @Test
    public void testRemoveOnEmptySet() {
        assertFalse(emptySet.remove(testInteger));
    }

    @Test
    public void testClear() {
        filledWithTestDataSet.clear();
        assertTrue(filledWithTestDataSet.isEmpty());
    }

    @Test
    public void testClearOnEmptySet() {
        emptySet.clear();
        assertTrue(emptySet.isEmpty());
    }

    @Test
    public void testContainsAll() {
        assertTrue(filledWithTestDataSet.containsAll(Arrays.asList(testData)));
    }

    @Test
    public void testContainsAllWhenContainNotOnlyTestedCollection() {
        filledWithTestDataSet.add(testInteger);
        assertTrue(filledWithTestDataSet.containsAll(Arrays.asList(testData)));
    }

    @Test
    public void testContainsAllWhenNotContain() {
        assertFalse(filledWithTestDataSet.containsAll(Arrays.asList(new Integer[]{testInteger})));
    }

    @Test(expected = NullPointerException.class)
    public void testContainsAllWithNullParameterShouldThrowNullPointerException() {
        filledWithTestDataSet.containsAll(null);
    }

    @Test
    public void testContainsAllOnEmptySet() {
        assertFalse(emptySet.containsAll(Arrays.asList(testData)));
    }

    @Test(expected = NullPointerException.class)
    public void testContainAllOnEmptySetWithNullParameterShouldThrowNullPointerException() {
        emptySet.containsAll(null);
    }

    @Test
    public void testAddAll() {
        assertTrue(emptySet.addAll(Arrays.asList(testData)));
    }

    @Test
    public void testAddAllWhenContain() {
        assertFalse(filledWithTestDataSet.addAll(Arrays.asList(testData)));
    }

    @Test
    public void testAddAllWithParameterContainsRepeatedElements() {
        final int SIZE = 2;
        Collection<Integer> testCollection = new ArrayList<Integer>();
        for (int i = 0; i < SIZE; i++)
            testCollection.add(testInteger);
        assertTrue(emptySet.addAll(testCollection));
        assertEquals(1, emptySet.size());
    }

    @Test
    public void testAddAllWhenSetContainSomeElementsFromCollection() {
        Collection<Integer> testCollection = new ArrayList<Integer>(Arrays.asList(testData));
        testCollection.add(testInteger);
        assertTrue(filledWithTestDataSet.addAll(testCollection));
        assertEquals(FILLED_SET_SIZE + 1, filledWithTestDataSet.size());
    }

    @Test(expected = NullPointerException.class)
    public void testAddAllWithNullParameter() {
        filledWithTestDataSet.addAll(null);
    }

    @Test
    public void testRemoveAll() {
        Collection<Integer> testCollection = new ArrayList<Integer>(Arrays.asList(testData));
        assertTrue(filledWithTestDataSet.removeAll(testCollection));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveAllWithNullParameterShouldThrowNullPointerException() {
        filledWithTestDataSet.removeAll(null);
    }

    @Test
    public void testRemoveAllWhenNotContain() {
        Collection<Integer> testCollection = new ArrayList<Integer>(Arrays.asList(new Integer[]{testInteger}));
        assertFalse(filledWithTestDataSet.removeAll(testCollection));
    }

    @Test
    public void testRemoveAllOnEmptySet() {
        assertFalse(emptySet.removeAll(new ArrayList()));
    }

    @Test
    public void testRetainAll() {
        Collection<Integer> testCollection = new ArrayList<Integer>(Arrays.asList(testData));
        filledWithTestDataSet.add(testInteger);
        assertTrue(filledWithTestDataSet.retainAll(testCollection));
        assertEquals(testData.length, filledWithTestDataSet.size());
    }

    @Test
    public void testRetainAllWhenCollectionAndSetAreEquals() {
        Collection<Integer> testCollection = new ArrayList<Integer>(Arrays.asList(testData));
        assertFalse(filledWithTestDataSet.retainAll(testCollection));
    }

    @Test(expected = NullPointerException.class)
    public void testRetainAllWithNullParameterShouldThrowNullPointerException() {
        filledWithTestDataSet.retainAll(null);
    }

    @Test
    public void testToArray() {
        Object[] result = filledWithTestDataSet.toArray();
        assertArrayEquals(testData, result);
    }

    @Test
    public void testToArrayShouldReturnSafeArray() {
        Object[] result = filledWithTestDataSet.toArray();
        result[0] = testInteger;
        assertFalse(filledWithTestDataSet.contains(testInteger));
    }

    @Test
    public void testToArrayWithZeroLengthArrayAsParameter() {
        Integer[] passed = new Integer[0];
        Integer[] returned = filledWithTestDataSet.toArray(passed);
        assertArrayEquals(testData, returned);
        assertNotSame(passed, returned);
    }

    @Test
    public void testToArrayWithActualSizedArrayAsParameter() {
        Integer[] passed = new Integer[FILLED_SET_SIZE];
        Integer[] returned = filledWithTestDataSet.toArray(passed);
        assertSame(passed, returned);
    }

    @Test
    public void testToArrayWithNotEmptyArrayAsParameter() {
        Integer[] passed = new Integer[FILLED_SET_SIZE * 2];
        for (int i = 0; i < passed.length; i++)
            passed[i] = i;
        Integer[] returned = filledWithTestDataSet.toArray(passed);
        assertEquals(passed.length, returned.length);
        for (int i = FILLED_SET_SIZE; i < returned.length; i++)
            assertEquals(null, returned[i]);
    }

    @Test(expected = NullPointerException.class)
    public void testToArrayWithNullParameter() {
        filledWithTestDataSet.toArray(null);
    }

    @Test
    public void testIterator() {
        Iterator<Integer> iterator = filledWithTestDataSet.iterator();
        Collection<Integer> testDataCollection = new ArrayList<Integer>(Arrays.asList(testData));
        int cnt = 0;
        while (iterator.hasNext()) {
            cnt++;
            assertTrue(testDataCollection.contains(iterator.next()));
        }
        assertEquals(FILLED_SET_SIZE, cnt);
    }

    @Test
    public void testIteratorRemove() {
        Iterator<Integer> iterator = filledWithTestDataSet.iterator();
        iterator.next();
        iterator.remove();
        assertEquals(FILLED_SET_SIZE - 1, filledWithTestDataSet.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testIteratorRemoveWithoutPreviewNextCallingShouldReturnIllegalStateException() {
        Iterator<Integer> iterator = filledWithTestDataSet.iterator();
        iterator.remove();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorWithSetConcurrentModificationShouldThrowConcurrentModificationException() {
        Iterator<Integer> iterator = filledWithTestDataSet.iterator();
        filledWithTestDataSet.remove(testData[0]);
        if (iterator.hasNext())
            iterator.next();
    }

    @Test
    public void testEquals() {
        Set<Integer> comparedSet = new HashSet<Integer>(Arrays.asList(testData));
        assertTrue(filledWithTestDataSet.equals(comparedSet));
    }

    @Test
    public void testEqualsWhenParameterIsNotSet() {
        List<Integer> comparedList = Arrays.asList(testData);
        assertFalse(filledWithTestDataSet.equals(comparedList));
    }

    @Test
    public void testEqualsWhenParameterIsNull() throws Exception {
        assertFalse(filledWithTestDataSet.equals(null));
    }
}
