package com.custom.set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

@RunWith(MockitoJUnitRunner.class)
public class ArraySetTestInternalState {
    static final int TESTED_SET_SIZE = 3;
    static final int INITIAL_CAPACITY = TESTED_SET_SIZE;
    static final Integer testInteger = Integer.MAX_VALUE;
    static final Integer[] testData = new Integer[TESTED_SET_SIZE];

    static {
        for (int i = 0; i < TESTED_SET_SIZE; i++)
            testData[i] = i;
    }

    @Spy
    ArraySet<Integer> testedSet = new ArraySet<Integer>(INITIAL_CAPACITY);

    @Before
    public void initialize() {
        setInternalState(testedSet, "size", TESTED_SET_SIZE);
        setInternalState(testedSet, "backingArray", testData);
    }

    @Test
    public void testAdd() {
        final int initialState = (Integer) getInternalState(testedSet, "modCount");
        testedSet.add(testInteger);
        final int finalState = (Integer) getInternalState(testedSet, "modCount");
        assertNotEquals(initialState, finalState);
    }

    @Test
    public void testRemove() {
        final int initialState = (Integer) getInternalState(testedSet, "modCount");
        testedSet.remove(testData[0]);
        final int finalState = (Integer) getInternalState(testedSet, "modCount");
        assertNotEquals(initialState, finalState);
    }

    @Test
    public void testRemoveWhenNotContainShouldSaveState() {
        final int initialState = (Integer) getInternalState(testedSet, "modCount");
        testedSet.remove(testInteger);
        final int finalState = (Integer) getInternalState(testedSet, "modCount");
        assertEquals(initialState, finalState);
    }

    @Test
    public void testClear() {
        final int initialState = (Integer) getInternalState(testedSet, "modCount");
        testedSet.clear();
        final int finalState = (Integer) getInternalState(testedSet, "modCount");
        assertNotEquals(initialState, finalState);
    }

    @Test
    public void testContains() {
        final int initialState = (Integer) getInternalState(testedSet, "modCount");
        testedSet.contains(testData[0]);
        final int finalState = (Integer) getInternalState(testedSet, "modCount");
        assertEquals(initialState, finalState);
    }

    @Test
    public void testIsEmpty() {
        final int initialState = (Integer) getInternalState(testedSet, "modCount");
        testedSet.isEmpty();
        final int finalState = (Integer) getInternalState(testedSet, "modCount");
        assertEquals(initialState, finalState);
    }

    @Test
    public void testSize() {
        final int initialState = (Integer) getInternalState(testedSet, "modCount");
        testedSet.size();
        final int finalState = (Integer) getInternalState(testedSet, "modCount");
        assertEquals(initialState, finalState);
    }
}
