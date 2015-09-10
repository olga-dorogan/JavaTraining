package com.custom;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by olga on 05.11.14.
 */
public class ListTest {

    List<String> emptyList;
    List<String> filledList;
    static final int INITIAL_CAPACITY = 5;
    static final int FILLED_LIST_SIZE = 3;
    String testString = new String("a");

    @Before
    public void initialize() {
        emptyList = new ArrayList<String>(INITIAL_CAPACITY);
        filledList = new ArrayList<String>(INITIAL_CAPACITY);
        for (int i = 0; i < FILLED_LIST_SIZE; i++)
            filledList.add(String.valueOf(i));
    }

    @Test
    public void testSize() {
        assertEquals(FILLED_LIST_SIZE, filledList.size());
    }

    @Test
    public void testSizeOnEmptyListShouldReturnZero() {
        assertEquals(0, emptyList.size());
    }

    @Test
    public void testAdd() {
        assertTrue(emptyList.add(testString));
        assertSame(testString, emptyList.get(0));
    }

    @Test
    public void testAddNullShouldAddCorrectly() {
        assertTrue(emptyList.add(null));
    }

    @Test
    public void testAddToPosition() {
        filledList.add(FILLED_LIST_SIZE, testString);
        assertSame(testString, filledList.get(FILLED_LIST_SIZE));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddToIllegalPositionShouldThrowIndexOutOfBoundsException() {
        filledList.add(FILLED_LIST_SIZE + 1, testString);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddToNegativePositionShouldThrowsIndexOutOfBoundsException() {
        filledList.add(-1, testString);
    }

    @Test
    public void testAddAll() {
        Collection<String> testCollection = Arrays.asList(new String[]{testString});
        assertTrue(filledList.addAll(testCollection));
        assertEquals(FILLED_LIST_SIZE + testCollection.size(), filledList.size());
    }

    @Test
    public void testAddAllWhenEmptyCollectionShouldReturnFalse() {
        Collection<String> testCollection = new ArrayList<String>();
        assertFalse(filledList.addAll(testCollection));
    }

    @Test(expected = NullPointerException.class)
    public void testAddAllWhenNullShouldThrowNullPointerException() {
        filledList.addAll(null);
    }

    @Test
    public void testAddAllToPosition() {
        Collection<String> testCollection = Arrays.asList(new String[]{testString});
        assertTrue(filledList.addAll(FILLED_LIST_SIZE, testCollection));
        assertEquals(FILLED_LIST_SIZE + testCollection.size(), filledList.size());
    }

    @Test
    public void testAddAllToPositionWhenEmptyCollectionShouldReturnFalse() {
        Collection<String> testCollection = new ArrayList<String>();
        assertFalse(filledList.addAll(0, testCollection));
    }

    @Test(expected = NullPointerException.class)
    public void testAddAllToPositionWhenNullShouldThrowNullPointerException() {
        filledList.addAll(FILLED_LIST_SIZE, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllToIllegalPositionShouldThrowIndexOutOfBoundsException() {
        Collection<String> testCollection = Arrays.asList(new String[]{testString});
        filledList.addAll(FILLED_LIST_SIZE + 1, testCollection);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllToNegativePositionShouldThrowIndexOutOfBoundsException() {
        filledList.addAll(-1, Arrays.asList(new String[]{testString}));
    }

    @Test
    public void testClear() {
        filledList.clear();
        assertEquals(0, filledList.size());
    }

    @Test
    public void testClearOnEmptyListShouldClearCorrectly() {
        emptyList.clear();
    }


    @Test
    public void testContainsWhenContain() {
        filledList.add(testString);
        assertTrue(filledList.contains(testString));
    }

    @Test
    public void testContainsWhenNotContain() {
        assertFalse(filledList.contains(testString));
    }

    @Test
    public void testContainsOnEmptyList() {
        assertFalse(emptyList.contains(testString));
    }

    @Test
    public void testContainsWithNull() {
        assertFalse(filledList.contains(null));
        filledList.add(null);
        assertTrue(filledList.contains(null));
    }

    @Test
    public void testGet() {
        filledList.add(FILLED_LIST_SIZE, testString);
        assertSame(testString, filledList.get(FILLED_LIST_SIZE));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFromIllegalPositionShouldThrowIndexOutOfBoundsException() {
        filledList.get(FILLED_LIST_SIZE);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFromNegativePositionShouldThrowIndexOutOfBoundsException() {
        filledList.get(-1);
    }

    @Test
    public void testIndexOfWhenContain() {
        filledList.add(FILLED_LIST_SIZE, testString);
        assertEquals(FILLED_LIST_SIZE, filledList.indexOf(testString));
    }

    @Test
    public void testIndexOfWhenNotContain() {
        assertEquals(-1, filledList.indexOf(testString));
    }

    @Test
    public void testIndexOfOnEmptyList() {
        assertEquals(-1, emptyList.indexOf(testString));
    }

    @Test
    public void testIsEmptyOnEmptyList() {
        assertTrue(emptyList.isEmpty());
    }

    @Test
    public void testIsEmptyOnNotEmptyList() {
        assertFalse(filledList.isEmpty());
    }

    @Test
    public void testLastIndexOfWhenContain() {
        filledList.add(testString);
        filledList.add(testString);
        assertEquals(filledList.size() - 1, filledList.lastIndexOf(testString));
    }

    @Test
    public void testLastIndexOfWhenNotContain() {
        assertEquals(-1, filledList.lastIndexOf(testString));
    }

    @Test
    public void testLastIndexOfWhenNull() {
        assertEquals(-1, filledList.lastIndexOf(null));
    }

    @Test
    public void testLastIndexOfOnEmptyList() {
        assertEquals(-1, emptyList.lastIndexOf(testString));
    }

    @Test
    public void testRemoveFromPosition() {
        final int position = FILLED_LIST_SIZE;
        filledList.add(position, testString);
        String removedString = filledList.remove(position);
        assertSame(testString, removedString);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveFromPositionOnEmptyListShouldThrowIndexOfBoundsException() {
        emptyList.remove(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveFromNegativePositionShouldThrowIndexOfBoundsException() {
        filledList.remove(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveFromIllegalPositionShouldThrowIndexOfBoundsException() {
        filledList.remove(FILLED_LIST_SIZE);
    }

    @Test
    public void testRemoveObjectWhenContain() {
        filledList.add(testString);
        assertTrue(filledList.remove(testString));
    }

    @Test
    public void testRemoveObjectWhenNotContain() {
        assertFalse(filledList.remove(testString));
    }

    @Test
    public void testRemoveObjectFromEmptyList() {
        assertFalse(emptyList.remove(testString));
    }

    @Test
    public void testRemoveObjectWithNullParameterShouldRemoveCorrectly() {
        filledList.remove(null);
    }

    @Test
    public void testRemoveObjectWithIncompatibleTypes() {

        assertFalse(filledList.remove(new Double(1.0)));
    }

    @Test
    public void testRemoveObjectWhenEmptyListAndIncompatibleTypes() {
        assertFalse(emptyList.remove(new Double(1.0)));
    }

    @Test
    public void testRemoveAllWhenContainCollection() {
        Collection<String> testCollection = Arrays.asList(new String[]{testString});
        filledList.addAll(testCollection);
        assertTrue(filledList.removeAll(testCollection));
        assertFalse(filledList.containsAll(testCollection));
        assertFalse(filledList.isEmpty());
    }

    @Test
    public void testRemoveAllWhenNotContainCollection() {
        Collection<String> testCollection = Arrays.asList(new String[]{testString});
        assertFalse(filledList.removeAll(testCollection));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveAllWithNullParameterShouldReturnNullPointerException() {
        filledList.removeAll(null);
    }

    @Test
    public void testRemoveAllWithMultiplyCollectionEntry() {
        Collection<String> testCollection = Arrays.asList(new String[]{testString});
        filledList.addAll(testCollection);
        filledList.addAll(testCollection);
        assertTrue(filledList.removeAll(testCollection));
        assertEquals(FILLED_LIST_SIZE, filledList.size());
    }

    @Test
    public void testRemoveAllWithCollectionContainsNull() {
        Collection<String> testCollection = Arrays.asList(new String[]{testString, null});
        filledList.addAll(testCollection);
        filledList.add(null);
        assertTrue(filledList.removeAll(testCollection));
        assertEquals(FILLED_LIST_SIZE, filledList.size());
    }

    @Test
    public void testRetainAllWhenContainOnlyThisCollection() {
        Collection<String> testCollection = Arrays.asList(new String[]{testString});
        emptyList.addAll(testCollection);
        assertFalse(emptyList.retainAll(testCollection));
    }

    @Test
    public void testRetainAllWhenContainNotOnlyThisCollection() {
        Collection<String> testCollectionFirst = Arrays.asList(new String[]{testString});
        Collection<String> testCollectionSecond = Arrays.asList(new String[]{new String("b")});
        filledList.addAll(testCollectionFirst);
        filledList.addAll(testCollectionSecond);
        assertTrue(filledList.retainAll(testCollectionFirst));
        assertEquals(testCollectionFirst.size(), filledList.size());
        assertFalse(filledList.containsAll(testCollectionSecond));
    }

    @Test
    public void testRetainAllWhenNotContain() {
        Collection<String> testCollection = Arrays.asList(new String[]{testString});
        assertTrue(filledList.retainAll(testCollection));
        assertEquals(0, filledList.size());
    }

    @Test
    public void testRetainAllWhenParameterIsEmptyCollection() {
        assertTrue(filledList.retainAll(new ArrayList<String>()));
        assertTrue(filledList.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void testRetainAllWhenParameterIsNullShouldThrowNullPointerException() {
        filledList.retainAll(null);
    }

    @Test
    public void testRetainAllWithIncompatibleElementTypesShouldClearList() {
        Collection<Integer> testCollection = Arrays.asList(new Integer[]{1, 2});
        filledList.retainAll(testCollection);
        assertTrue(filledList.isEmpty());
    }

    @Test
    public void testSet() {
        final int position = FILLED_LIST_SIZE;
        String anotherString = new String("b");
        filledList.add(position, testString);
        assertSame(testString, filledList.set(position, anotherString));
        assertSame(anotherString, filledList.get(position));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetToEmptyListShouldThrowIndexOfBoundException() {
        emptyList.set(0, testString);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetToNegativePositionShouldThrowIndexOfBoundsException() {
        filledList.set(-1, testString);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetToIllegalPositionShouldThrowIndexOfBoundsException() {
        filledList.set(FILLED_LIST_SIZE, testString);
    }

    @Test
    public void testSortOnComparableItemsOnNotEmptyListWithComparator() {
        final int a1 = 1, a2 = 2;
        ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(new Integer[]{a1, a2}));
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        assertTrue(Math.min(a1, a2) == list.get(0));
        assertTrue(Math.max(a1, a2) == list.get(1));
    }

    @Test
    public void testSortOnComparableItemsOnEmptyListWithComparator() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
    }

    @Test
    public void testSortOnComparableItemsOnNotEmptyListWithNullComparator() {
        final int a1 = 1, a2 = 2;
        ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(new Integer[]{a1, a2}));
        list.sort(null);
        assertTrue(Math.min(a1, a2) == list.get(0));
        assertTrue(Math.max(a1, a2) == list.get(1));
    }

    @Test
    public void testSortOnComparableItemsOnEmptyListWithNullComparator() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.sort(null);
    }

    @Test
    public void testSortOnNotComparableItemsOnEmptyListWithComparator() {
        List<Object> list = new ArrayList<Object>();
        list.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
    }

    @Test
    public void testSortOnNotComparableItemsOnEmptyListWithNullComparator() {
        List<Object> list = new ArrayList<Object>();
        list.sort(null);
    }

    @Test
    public void testSortOnNotComparableItemsOnNotEmptyListWithComparator() {
        List<Object> list = new ArrayList<Object>();
        list.add(new Object());
        list.add(new Object());
        list.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
    }

    @Test
    public void testSortOnNotComparableItemsOnListWithOneItemWithNullComparator() {
        List<Object> list = new ArrayList<Object>();
        list.add(new Object());
        list.sort(null);
    }

    @Test(expected = ClassCastException.class)
    public void testSortOnNotComparableItemsOnNotEmptyListWithNullComparatorShouldThrowClassCastException() {
        List<Object> list = new ArrayList<Object>();
        Object objFirst = new Object();
        Object objSecond = new Object();
        list.add(objFirst);
        list.add(objSecond);
        try {
            list.sort(null);
        } catch (ClassCastException e) {
            assertEquals(
                    list.get(0).getClass().getCanonicalName() + " cannot be cast to java.lang.Comparable",
                    e.getMessage());
            throw e;
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSortOnComparableItemsOnUnmodifiableListShouldThrowUnsupportedOperationException() {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(new Integer[]{1, 2}));
        List<Integer> unmodifiableList = Collections.unmodifiableList(list);
        unmodifiableList.sort(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSortOnComparableItemsOnUnmodifiableEmptyListShouldThrowUnsupportedOperationException() {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> unmodifiableList = Collections.unmodifiableList(list);
        unmodifiableList.sort(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSortOnNotComparableItemsOnUnmodifiableListShouldThrowUnsupportedOperationException() {
        List<Object> list = new ArrayList<Object>();
        list.add(new Object());
        list.add(new Object());
        List<Object> unmodifiableList = Collections.unmodifiableList(list);
        unmodifiableList.sort(null);
    }

    @Test
    public void testSubList() {
        final int LIST_SIZE = 5;
        final int fromIndex = 1, toIndex = 3;
        for (int i = FILLED_LIST_SIZE; i < LIST_SIZE; i++)
            filledList.add(i, String.valueOf(i));

        List<String> subList = filledList.subList(fromIndex, toIndex);
        assertEquals((toIndex - fromIndex), subList.size());
        for (int i = fromIndex; i < toIndex; i++)
            assertEquals(filledList.get(i), subList.get(i - fromIndex));
    }

    @Test
    public void testSubListShouldReturnNotSafeSubList() {
        final int LIST_SIZE = 5;
        final int fromIndex = 1, toIndex = 3;
        for (int i = FILLED_LIST_SIZE; i < LIST_SIZE; i++)
            filledList.add(i, String.valueOf(i));

        List<String> subList = filledList.subList(fromIndex, toIndex);
        subList.set(0, testString);
        assertEquals(subList.get(0), filledList.get(fromIndex));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubListWhenIntervalUnorderedShouldThrowIllegalArgumentException() {
        final int fromIndex = FILLED_LIST_SIZE - 1;
        final int toIndex = 0;
        try {
            filledList.subList(fromIndex, toIndex);

        } catch (IllegalArgumentException e) {
            String expectedMsg = String.format("fromIndex(%d) > toIndex(%d)", fromIndex, toIndex);
            assertEquals(expectedMsg, e.getMessage());
            throw e;
        }
    }

    @Test
    public void testSubListWhenIntervalBoundsEqualShouldReturnEmptyList() {
        assertTrue(filledList.subList(FILLED_LIST_SIZE - 1, FILLED_LIST_SIZE - 1).isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListWhenFromIndexOutOfRangeShouldThrowIndexOfBoundsException() {
        final int fromIndex = -1;
        try {
            filledList.subList(fromIndex, FILLED_LIST_SIZE - 1);
        } catch (IndexOutOfBoundsException e) {
            String expectedMsg = String.format("fromIndex = %d", fromIndex);
            assertEquals(expectedMsg, e.getMessage());
            throw e;
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListWhenToIndexOutOfRangeShouldThrowIndexOfBoundsException() {
        final int toIndex = FILLED_LIST_SIZE + 1;
        try {
            filledList.subList(0, toIndex);
        } catch (IndexOutOfBoundsException e) {
            String expectedMsg = String.format("toIndex = %d", toIndex);
            assertEquals(expectedMsg, e.getMessage());
            throw e;
        }
    }

    @Test
    public void testToArrayShouldReturnObjectArray() {
        Object[] arr = filledList.toArray();
        assertEquals(filledList.size(), arr.length);
        for (int i = 0; i < FILLED_LIST_SIZE; i++)
            assertEquals(filledList.get(i), arr[i]);
    }

    @Test
    public void testToArrayShouldReturnSafeObjectArray() {
        Object[] arr = filledList.toArray();
        arr[0] = testString;
        assertNotEquals(arr[0], filledList.get(0));
    }

    @Test
    public void testToArrayOnEmptyListShouldReturnEmptyArray() {
        Object[] arr = emptyList.toArray();
        assertEquals(0, arr.length);
    }

    @Test
    public void testToArrayWhenParameterIsZeroLengthArrayShouldReturnNotZeroLengthArray() {
        Object[] arr = filledList.toArray(new String[0]);
        assertTrue(arr[0] instanceof String);
        assertEquals(filledList.get(0), arr[0]);
    }

    @Test
    public void testToArrayWhenParameterIsActualSizedArrayShouldReturnSameArray() {
        String[] arrPassed = new String[filledList.size()];
        String[] arrReturned = filledList.toArray(arrPassed);
        assertSame(arrPassed, arrReturned);
    }

    @Test
    public void testToArrayWhenParameterIsNotActualSizedArrayShouldReturnAnotherArray() {
        String[] arrPassed = new String[filledList.size() - 1];
        String[] arrReturned = filledList.toArray(arrPassed);
        assertNotSame(arrPassed, arrReturned);
    }

    @Test
    public void testToArrayOnEmptyListWhenListAndArrayTypesAreDifferentShouldReturnEmptyArray() {
        Double[] arr = emptyList.toArray(new Double[0]);
        assertEquals(0, arr.length);
    }

    @Test(expected = ArrayStoreException.class)
    public void testToArrayWhenListAndArrayTypesAreDifferentShouldThrowArrayStoreException() {
        Double[] arr = filledList.toArray(new Double[0]);
    }

    @Test(expected = NullPointerException.class)
    public void testToArrayWhenParameterIsNullShouldThrowNullPointerException() {
        Object[] arr = filledList.toArray(null);
    }

    @Test
    public void testIterator() {
        List<String> list = new ArrayList<String>();
        String[] arrStr = {new String("a"), new String("b")};
        list.addAll(Arrays.asList(arrStr));
        Iterator<String> iterator = list.iterator();
        assertNotEquals(iterator, null);

        int cnt = 0;
        while (iterator.hasNext())
            assertEquals(arrStr[cnt++], iterator.next());
        assertEquals(list.size(), cnt);
    }

    @Test
    public void testIteratorOnEmptyList() {
        Iterator iterator = emptyList.iterator();
        assertNotEquals(iterator, null);
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testListIterator() {
        List<String> list = new ArrayList<String>();
        String[] arrStr = {new String("a"), new String("b")};
        list.addAll(Arrays.asList(arrStr));
        ListIterator<String> listIterator = list.listIterator();
        assertNotEquals(listIterator, null);

        int cnt = 0;
        while (listIterator.hasNext())
            assertEquals(arrStr[cnt++], listIterator.next());
        assertEquals(list.size(), cnt);
        cnt--;
        while (listIterator.hasPrevious())
            assertEquals(arrStr[cnt--], listIterator.previous());
    }

    @Test
    public void testListIteratorOnEmptyList() {
        ListIterator<String> listIterator = emptyList.listIterator();
        assertNotEquals(listIterator, null);
        assertFalse(listIterator.hasNext());
        assertFalse(listIterator.hasPrevious());
    }
}
