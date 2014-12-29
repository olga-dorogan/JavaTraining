package com.custom.set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ArraySetTestBehavior {
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
        testedSet.add(testInteger);
        verify(testedSet).add(testInteger);
        verify(testedSet, times(1)).contains(testInteger);
        verifyNoMoreInteractions(testedSet);
    }

    @Test
    public void testAddAll() {
        Collection<Integer> testCollection = Arrays.asList(new Integer[]{testInteger, testInteger - 1, testInteger - 2});
        testedSet.addAll(testCollection);
        InOrder inOrder = inOrder(testedSet);
        inOrder.verify(testedSet, times(1)).addAll(testCollection);
        for (int i = 0; i < testCollection.size(); i++) {
            inOrder.verify(testedSet).add(anyInt());
            inOrder.verify(testedSet).contains(anyInt());
        }
        verifyNoMoreInteractions(testedSet);
    }

    @Test
    public void testContainsAll() {
        Collection<Integer> testCollection = Arrays.asList(new Integer[]{testInteger, testInteger - 1});
        testedSet.containsAll(testCollection);
        verify(testedSet).containsAll(testCollection);
        verify(testedSet, atLeastOnce()).contains(anyInt());
        verifyNoMoreInteractions(testedSet);
    }

    @Test
    public void testRemoveAll() {
        Collection<Integer> testCollection = Arrays.asList(new Integer[]{testInteger, testInteger - 1});
        testedSet.removeAll(testCollection);
        verify(testedSet).removeAll(testCollection);
        InOrder inOrder = inOrder(testedSet);
        inOrder.verify(testedSet).isEmpty();
        inOrder.verify(testedSet, times(testCollection.size())).remove(anyInt());
        verifyNoMoreInteractions(testedSet);
    }
}
