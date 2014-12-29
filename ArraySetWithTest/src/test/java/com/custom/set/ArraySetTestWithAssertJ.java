package com.custom.set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by olga on 18.11.14.
 */

public class ArraySetTestWithAssertJ {
    static final int TESTED_SET_SIZE = 3;
    static final Integer testInteger = Integer.MAX_VALUE;
    static final Integer[] testData = new Integer[TESTED_SET_SIZE];

    static {
        for (int i = 0; i < TESTED_SET_SIZE; i++)
            testData[i] = i;
    }

    Set<Integer> testedSet;

    @Before
    public void initialize() {
        testedSet = new ArraySet<Integer>(Arrays.asList(testData));
    }

    @Test
    public void testSize() {
        assertThat(testedSet).hasSize(TESTED_SET_SIZE);
    }

    @Test
    public void testAdd() {
        testedSet.add(testInteger);
        assertThat(testedSet).contains(testInteger).hasSize(TESTED_SET_SIZE + 1);
    }

    @Test
    public void testAddAll() {
        Collection<Integer> testCollection = Arrays.asList(new Integer[]{testInteger, testInteger - 1});
        testedSet.addAll(testCollection);
        assertThat(testedSet).containsAll(testCollection).hasSize(TESTED_SET_SIZE + testCollection.size());
    }

    @Test
    public void testRemoveAll() {
        Collection<Integer> testCollection = Arrays.asList(testData);
        testedSet.removeAll(testCollection);
        assertThat(testedSet).doesNotContainAnyElementsOf(testCollection).isEmpty();
    }

    @Test
    public void testRetailAll() {
        Collection<Integer> testCollection = Arrays.asList(testData);
        testedSet.add(testInteger);
        testedSet.retainAll(testCollection);
        assertThat(testedSet).containsOnlyElementsOf(testCollection);
    }
}
