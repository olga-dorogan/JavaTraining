package com.custom;

import java.io.*;

/**
 * Created by olga on 12.12.14.
 */
public class Main {
    private static final int STREAM_SIZE = 1000000; // 1 Mb

    private static final int[] TESTED_BUFF_SIZES = {1, 8000, 16000, 32000, 64000, 128000, 256000, 512000};
    private static final int DIFF_BUFFS_ITERATIONS = 3;

    private static final int WARMUP_STREAM_SIZE = 100;
    private static final int WARMUP_BUFFER_SIZE = WARMUP_STREAM_SIZE / 4;

    private static File ioFile = new File("testedFile.dat");


    public static void main(String[] args) {
        warmupForMemoryIO();
        getStatisticAboutPerformanceOfBufferedMemoryIO();
        try {
            warmupForFileIO();
            getStatisticAboutPerformanceOfBufferedFileIO();
        } catch (FileNotFoundException e) {
            System.out.println("Internal error: temporary file was not created.");
            e.printStackTrace();
        }
    }

    private static void warmupForMemoryIO() {
        getTimeForBufferedIO(
                new ByteArrayOutputStream(STREAM_SIZE),
                new ByteArrayInputStream(new byte[STREAM_SIZE]),
                WARMUP_STREAM_SIZE,
                WARMUP_BUFFER_SIZE);
        getTimeForUnbufferedIO(
                new ByteArrayOutputStream(STREAM_SIZE),
                new ByteArrayInputStream(new byte[STREAM_SIZE]),
                WARMUP_STREAM_SIZE);
        System.gc();
    }

    private static void warmupForFileIO() throws FileNotFoundException {
        clearFile();
        getTimeForBufferedIO(
                new FileOutputStream(ioFile),
                new FileInputStream(ioFile),
                WARMUP_STREAM_SIZE,
                WARMUP_BUFFER_SIZE);
        clearFile();
        getTimeForUnbufferedIO(
                new FileOutputStream(ioFile),
                new FileInputStream(ioFile),
                WARMUP_STREAM_SIZE);
        clearFile();
        System.gc();
    }

    private static void getStatisticAboutPerformanceOfBufferedMemoryIO() {
        System.out.println("Memory IO");
        System.out.println("Buffer size\t\tAverage\t\tDeviation\tUnbufIO time / BufIO time");
        double unbufTime = doExperimentOnMemoryIOWithoutBuffer();
        for (int i = 0; i < TESTED_BUFF_SIZES.length; i++) {
            doExperimentOnMemoryIOWithBuffer(TESTED_BUFF_SIZES[i], unbufTime);
        }
    }

    private static void doExperimentOnMemoryIOWithBuffer(int buffSize, double unbufTime) {
        long[] timeForBufferedIO = new long[DIFF_BUFFS_ITERATIONS];

        for (int i = 0; i < DIFF_BUFFS_ITERATIONS; i++) {
            timeForBufferedIO[i] = getTimeForBufferedIO(
                    new ByteArrayOutputStream(STREAM_SIZE),
                    new ByteArrayInputStream(new byte[STREAM_SIZE]),
                    STREAM_SIZE,
                    buffSize);
            System.gc();
        }
        processResultsForOptimalBufferSize(buffSize, timeForBufferedIO, unbufTime);
    }

    private static double doExperimentOnMemoryIOWithoutBuffer() {
        long[] timeForUnbufferedIO = new long[DIFF_BUFFS_ITERATIONS];

        for (int i = 0; i < DIFF_BUFFS_ITERATIONS; i++) {
            timeForUnbufferedIO[i] = getTimeForUnbufferedIO(
                    new ByteArrayOutputStream(STREAM_SIZE),
                    new ByteArrayInputStream(new byte[STREAM_SIZE]),
                    STREAM_SIZE);
            System.gc();
        }
        return processResultsForOptimalBufferSize(0, timeForUnbufferedIO, 0);
    }


    private static void getStatisticAboutPerformanceOfBufferedFileIO() throws FileNotFoundException {
        System.out.println("File IO");
        System.out.println("Buffer size\t\tAverage\t\tDeviation\tUnbufIO time / BufIO time");
        double unbufTime = doExperimentOnFileIOWithoutBuffer();
        for (int i = 0; i < TESTED_BUFF_SIZES.length; i++) {
            doExperimentOnFileIOWithBuffer(TESTED_BUFF_SIZES[i], unbufTime);
        }
    }

    private static void doExperimentOnFileIOWithBuffer(int buffSize, double unbufTime)
            throws FileNotFoundException {
        long[] timeForBufferedIO = new long[DIFF_BUFFS_ITERATIONS];

        for (int i = 0; i < DIFF_BUFFS_ITERATIONS; i++) {
            timeForBufferedIO[i] = getTimeForBufferedIO(
                    new FileOutputStream(ioFile),
                    new FileInputStream(ioFile),
                    STREAM_SIZE,
                    buffSize);
            clearFile();
            System.gc();
        }
        processResultsForOptimalBufferSize(buffSize, timeForBufferedIO, unbufTime);
    }

    private static double doExperimentOnFileIOWithoutBuffer() throws FileNotFoundException {
        long[] timeForUnbufferedIO = new long[DIFF_BUFFS_ITERATIONS];

        for (int i = 0; i < DIFF_BUFFS_ITERATIONS; i++) {
            timeForUnbufferedIO[i] = getTimeForUnbufferedIO(
                    new FileOutputStream(ioFile),
                    new FileInputStream(ioFile),
                    STREAM_SIZE);
            clearFile();
            System.gc();
        }
        return processResultsForOptimalBufferSize(0, timeForUnbufferedIO, 0);
    }

    private static double processResultsForOptimalBufferSize(int buffSize, long[] time, double unbufTime) {
        final double timeUnitsKoef = 1e6;
        final String timeUnits = "ms";
        double avg;
        double deviation;
        double diff;
        avg = 0;
        for (int i = 0; i < time.length; i++) {
            avg += time[i];
        }
        avg /= time.length;
        deviation = 0;
        for (int i = 0; i < time.length; i++) {
            diff = time[i] - avg;
            if (diff < 0) diff *= -1;
            deviation += diff;
        }
        deviation /= time.length;
        System.out.printf("%d%s\t\t%.2f %s\t\t%.2f %s\t\t%.2f\n", buffSize, buffSize < 100 ? "\t" : "",
                avg / timeUnitsKoef, timeUnits, deviation / timeUnitsKoef, timeUnits,
                unbufTime == 0 ? 1 : unbufTime / avg);
        return avg;
    }

    private static void clearFile() {
        try {
            ioFile.delete();
            ioFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long getTimeForUnbufferedIO(OutputStream outputStream, InputStream inputStream, int bytesCount) {
        int dummy;
        long startTime, stopTime;

        dummy = 0;
        startTime = System.nanoTime();
        try {
            for (int i = 0; i < bytesCount; i++)
                outputStream.write(0);
            for (int i = 0; i < bytesCount; i++)
                dummy |= inputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopTime = System.nanoTime();

        if (dummy != 0)
            System.out.println();

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (stopTime - startTime);
    }

    private static long getTimeForBufferedIO(OutputStream outputStream, InputStream inputStream,
                                             int bytesCount, int bufferLength) {
        int dummy;
        long startTime, stopTime;

        dummy = 0;
        startTime = System.nanoTime();
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, bufferLength);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, bufferLength)) {
            for (int i = 0; i < bytesCount; i++)
                bufferedOutputStream.write(0);
            bufferedOutputStream.flush();
            for (int i = 0; i < bytesCount; i++)
                dummy |= bufferedInputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopTime = System.nanoTime();

        if (dummy != 0)
            System.out.println();

        return (stopTime - startTime);
    }
}
