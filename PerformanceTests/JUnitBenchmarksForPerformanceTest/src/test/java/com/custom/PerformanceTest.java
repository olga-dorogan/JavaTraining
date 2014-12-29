package com.custom;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.h2.H2Consumer;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by olga on 08.12.14.
 */
@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart(filePrefix = "benchmark-lists")
@BenchmarkOptions(benchmarkRounds = 20, warmupRounds = 4)
public class PerformanceTest {
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule(h2consumer);
    private static H2Consumer h2consumer;

    private static final int COUNT = 100000;
    private static final int LIST_SIZE = 1000;
    private static List<Integer> list;
    private static int dummy;

    @BeforeClass
    public static void checkFile() throws SQLException {
        h2consumer = new H2Consumer(
                new File(PerformanceTest.class.getName()),
                new File("tmp-subdir"),
                null);
    }

    @BeforeClass
    public static void prepare() {
        list = new ArrayList<Integer>(LIST_SIZE);
        for (int i = 0; i < LIST_SIZE; i++)
            list.add(i);
    }

    @Test
    public void testExceptionProcessing() throws Exception {
        for (int i = 0; i < COUNT; i++) {
            try {
                methodThrowsException();
            } catch (Exception e) {
                dummy |= 1;
            }
        }
    }

    @Test
    public void testCStyleProcessing() throws Exception {
        for (int i = 0; i < COUNT; i++) {
            if (methodReturnsInCStyle() == -1) {
                dummy |= 1;
            }
        }
    }

    @Test
    public void testLogicProcessing() throws Exception {
        for (int i = 0; i < COUNT; i++) {
            dummy |= Collections.max(list);
            if (list.size() == LIST_SIZE)
                dummy |= 1;
        }
    }

    private void methodThrowsException() throws Exception {
        dummy |= Collections.max(list);
        if (list.size() == LIST_SIZE)
            throw new Exception();
    }

    private int methodReturnsInCStyle() {
        dummy |= Collections.max(list);
        if (list.size() == LIST_SIZE)
            return -1;
        return 0;
    }

}



 /*
    private static Object singleton = new Object();
    private static int COUNT = 50000;
    private static int[] rnd;

    @Test
    public void arrayList() throws Exception {
        runTest(new ArrayList<Object>());
    }

    @Test
    public void linkedList() throws Exception {
        runTest(new LinkedList<Object>());
    }

    @Test
    public void vector() throws Exception {
        runTest(new Vector<Object>());
    }

    private void runTest(List<Object> list) {
        assert list.isEmpty();

        // First, add a number of objects to the list.
        for (int i = 0; i < COUNT; i++)
            list.add(singleton);

        // Randomly delete objects from the list.
        for (int i = 0; i < rnd.length; i++)
            list.remove(rnd[i] % list.size());
    }
*/