package com.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by olga on 08.12.14.
 */
public class MainEntry {
    private static final boolean IS_VERBOSEGC = false;
    private static final int ROUNDS = 10;

    private static final int WARM_REPEATS = 100000;
    private static final int REPEATS = 1000000;

    private static final int SIZE = 10000;
    private static final List<Integer> list;

    private static int dummy;

    static {
        list = new ArrayList<Integer>(SIZE);
        for (int i = 0; i < SIZE; i++)
            list.add(i);
    }

    public static void main(String[] args) {
        doComparison(true);
        System.out.println("\nLogic\t\t\tC-style\t\t\t\tException\t\tException time/C-style time");
        for (int i = 0; i < ROUNDS; i++)
            doComparison(false);
        if (dummy < 0)
            System.out.println();
    }

    private static void doComparison(final boolean isWarmup) {
        final double timeCoef = 1e9;
        final String timeUnits = "s\t";

        long timeExceptionProcessing;
        long timeCStyleProcessing;
        long timeLogicProcessing;
        int repeats = isWarmup ? WARM_REPEATS : REPEATS;

        timeLogicProcessing = getLogicProcessingTime(repeats);
        timeExceptionProcessing = getExceptionProcessingTime(repeats);
        timeCStyleProcessing = getCStyleProcessingTime(repeats);
        if (isWarmup)
            return;

        timeCStyleProcessing -= timeLogicProcessing;
        timeExceptionProcessing -= timeLogicProcessing;

        System.out.printf("%.2f %s\t\t%.2f %s\t\t%.2f %s\t\t%.2f\n",
                timeLogicProcessing / timeCoef, timeUnits,
                timeCStyleProcessing / timeCoef, timeUnits,
                timeExceptionProcessing / timeCoef, timeUnits,
                (double) timeExceptionProcessing / timeCStyleProcessing);

    }

    private static long getExceptionProcessingTime(final int reps) {
        long startTime;
        long stopTime;

        System.gc();
        if (IS_VERBOSEGC)
            System.out.println("Start exception processing...");

        startTime = System.nanoTime();
        for (int i = 0; i < reps; i++) {
            try {
                methodThrowsException();
            } catch (Exception e) {
                dummy |= 1;
            }
        }
        stopTime = System.nanoTime();

        if (IS_VERBOSEGC)
            System.out.println("Stop exception processing...");

        return stopTime - startTime;
    }

    private static long getCStyleProcessingTime(final int reps) {
        long startTime;
        long stopTime;

        System.gc();
        if (IS_VERBOSEGC)
            System.out.println("Start c-style processing...");

        startTime = System.nanoTime();
        for (int i = 0; i < reps; i++) {
            if (methodReturnsInCStyle() == -1) {
                dummy |= 1;
            }
        }
        stopTime = System.nanoTime();

        if (IS_VERBOSEGC)
            System.out.println("Stop c-style processing...");

        return stopTime - startTime;
    }

    private static long getLogicProcessingTime(final int reps) {
        long startTime;
        long stopTime;

        System.gc();
        if (IS_VERBOSEGC)
            System.out.println("Start logic processing...");

        startTime = System.nanoTime();
        for (int i = 0; i < reps; i++) {
            dummy |= Collections.max(list);
            if (list.size() == SIZE)
                dummy |= 1;
        }
        stopTime = System.nanoTime();

        if (IS_VERBOSEGC)
            System.out.println("Stop logic processing...");

        return stopTime - startTime;
    }

    private static void methodThrowsException() throws Exception {
        dummy |= Collections.max(list);
        if (list.size() == SIZE)
            throw new Exception();
    }

    private static int methodReturnsInCStyle() {
        dummy |= Collections.max(list);
        if (list.size() == SIZE)
            return -1;
        return 0;
    }
}
