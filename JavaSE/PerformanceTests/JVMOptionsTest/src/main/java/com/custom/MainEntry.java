package com.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by olga on 08.12.14.
 */
// OPTIONS:
    // -Xint - to disable JIT optimization
    // -verbosegc - to print info about gc events
public class MainEntry {
    private static final int SIZE = 10000;
    private static List<Integer> list;

    static {
        list = new ArrayList<Integer>(SIZE);
        for (int i = 0; i < SIZE; i++)
            list.add(i);
    }

    public static void main(String[] args) {
        long startTime, stopTime;
        testMaxMethod(100);
        startTime = System.nanoTime();
        testMaxMethod(100);
        stopTime = System.nanoTime();
        System.out.printf("Diff is %.2f ms.\n", (double) (stopTime - startTime) / 1000000);
    }

    private static int getMaxFromList(List<Integer> testedList) {
        return Collections.max(testedList);
    }

    private static int testMaxMethod(int reps) {
        int dummy = 0;
        for (int i = 0; i < reps; i++) {
            //System.gc(); // to check info about gc events
            dummy |= getMaxFromList(list);
            list.add(i);
            list = new ArrayList<Integer>(list);
        }
        return dummy;
    }
}
