package com.custom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by olga on 08.01.15.
 */
public class Temporary {

    private static final int
        INIT_N_STREAMS = 1,
        RATE_N_STREAMS = 2,
        FINAL_N_STREAMS = 128;
    private static final int
            INIT_ARRAY_SIZE = 10,
            RATE_ARRAY_SIZE = 10,
            FINAL_ARRAY_SIZE = 1000000;

    private static final int ROUNDS_FOR_AVERAGE_TIME = 1;
    private static final int ROUNDS_FOR_PREWARM = 50;

    private static final String FILE_NAME = "data";


    private static class FullExperimentWithResultForExcel {
        private static void doExperiment(Object exampleObject) {
            Object testedData;
            long time;
            try (FileWriter writer = new FileWriter(new File(FILE_NAME + ".csv"))) {
                writer.write(";");
                for (int nStreams = INIT_N_STREAMS; nStreams <= FINAL_N_STREAMS; nStreams *= RATE_N_STREAMS) {
                    writer.write(String.valueOf(nStreams));
                    writer.write(';');
                }
                writer.write('\n');
                for (int size = INIT_ARRAY_SIZE; size <= FINAL_ARRAY_SIZE; size *= RATE_ARRAY_SIZE) {
                    writer.write(String.valueOf(size));
                    writer.write(';');
                    testedData = Experiment.createDataArray(exampleObject, size);
                    System.gc();

                    for (int nStreams = INIT_N_STREAMS; nStreams <= FINAL_N_STREAMS; nStreams *= RATE_N_STREAMS) {
                        time = Experiment.getAverageTimeForParallelSort(testedData, nStreams);
                        writer.write(String.valueOf(time));
                        writer.write(';');
                    }
                    writer.write('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class FullExperimentWithResultForGnuPlot {
        /**
         * The function fills the csv-file by times of the sort of the arrays
         * with different sizes and different amount of streams, in which the sort
         * is processed. The type of the array elements is the same as the type
         * of each element in the exampleObject.
         *
         * @param exampleObject - the array, which is used to identify the type
         *                      of the tested data. It can be an array of int values
         *                      or of any reference type values. The array must
         *                      contain at least one element.
         */
        private static void doExperiment(Object exampleObject) {
            StringBuilder sb = new StringBuilder();
            // testedData can be int[] or Object[] depending on exampleObject type
            Object testedData;
            long time;
            try (FileWriter writer = new FileWriter(new File(FILE_NAME + ".txt"))) {
                writer.write("#ArraySize nStreams Time(ns)\n");
                for (int size = INIT_ARRAY_SIZE; size <= FINAL_ARRAY_SIZE; size *= RATE_ARRAY_SIZE) {
                    testedData = Experiment.createDataArray(exampleObject, size);
                    System.gc();

                    for (int nStreams = INIT_N_STREAMS; nStreams <= FINAL_N_STREAMS; nStreams *= RATE_N_STREAMS) {
                        time = Experiment.getAverageTimeForParallelSort(testedData, nStreams);
                        sb.append(size).append("\t").append(nStreams).append("\t").append(time).append('\n');
                        writer.write(sb.toString());
                        sb.setLength(0);
                    }
                    writer.write('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Creates the file in the specified format, which can be used as input data
         * for GnuPlot. This file can be processed by GnuPlot by using
         * the sequence of shell commands 'cat data.graph|gnuplot'.
         */
        private static void createGraphFileForGnuPlot() {
            File dataFile = new File(FILE_NAME + ".graph");
            try (FileWriter writer = new FileWriter(dataFile)) {
                writer.write("#! /usr/bin/gnuplot -persist\n" +
                        "set terminal jpeg size 640, 480\n" +
                        "set output \"" + FILE_NAME + ".jpg\"\n" +
                        "set logscale y\n" +
                        "set ylabel \"Array size\"\n" +
                        "set xlabel \"Streams count\"\n" +
                        "set zlabel \"Time (ns)\" rotate by 90\n" +
                        "set grid ytics lc rgb \"#bbbbbb\" lw 1 lt 0\n" +
                        "set grid xtics lc rgb \"#bbbbbb\" lw 1 lt 0\n" +
                        "set grid ztics lc rgb \"#bbbbbb\" lw 1 lt 0\n" +
                        "set pm3d\n" +
                        "splot \"" + FILE_NAME + ".txt\" using 2:1:3 with lines notitle");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static class Experiment {
        /**
         * @param obj - the array, which is used to identify the type
         *            of the tested data. It can be an array of int values
         *            or of any reference type values. The array must
         *            contain at least one element.
         */
        private static void preWarm(Object obj) {
            Object testedData;
            long time = 0;
            for (int r = 0; r < ROUNDS_FOR_PREWARM; r++) {
                for (int size = INIT_ARRAY_SIZE; size <= FINAL_ARRAY_SIZE; size *= RATE_ARRAY_SIZE) {
                    testedData = createDataArray(obj, size);
                    for (int nStreams = INIT_N_STREAMS; nStreams <= FINAL_N_STREAMS; nStreams *= RATE_N_STREAMS) {
                        time |= getAverageTimeForParallelSort(testedData, nStreams);
                    }
                }
            }
            if (time < 0) System.out.println();
        }

        /**
         * The function creates the array with specified size, in which
         * the type of every element is the same as the type of the first
         * element of the first parameter (data).
         *
         * @param data - the array, which is used ti identify the type of each element
         *             in new array. Must be the array of int values or any reference type
         *             values and must contain at least ont element.
         * @param size - the size of new array.
         * @return new array filled with random data
         */
        private static Object createDataArray(Object data, final int size) {
            if (data instanceof int[]) {
                int[] result = new int[size];
                Arrays.setAll(result, (x) -> (int) (Math.random() * 100));
                return result;
            } else {
                String className = (((Object[]) data)[0]).getClass().getCanonicalName();
                Object[] result = new Object[size];
                try {
                    for (int i = 0; i < size; i++)
                        result[i] = Class.forName(className).newInstance();
                    return result;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        private static long getAverageTimeForParallelSort(Object testedData, int nStreams) {
            double sum = 0;
            for (int i = 0; i < ROUNDS_FOR_AVERAGE_TIME; i++)
                sum += getTimeForParallelSort(testedData, nStreams);
            return (long) (sum / ROUNDS_FOR_AVERAGE_TIME);
        }

        private static long getTimeForParallelSort(Object testedData, int nStreams) {
            long time;
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(nStreams - 1));
            if (testedData instanceof int[])
                time = getTime((int[]) testedData);
            else
                time = getTime((Object[]) testedData);
            return time;
        }

        private static long getTime(int[] testedData) {
            long time = System.nanoTime();
            Arrays.stream(testedData).parallel().sorted();
            time = System.nanoTime() - time;
            return time;
        }

        private static long getTime(Object[] testedData) {
            long time = System.nanoTime();
            Arrays.stream(testedData).parallel().sorted();
            time = System.nanoTime() - time;
            return time;
        }

        private static void checkParallelStreams(int nStreams) {
            long start = System.currentTimeMillis();
            IntStream s = IntStream.range(0, nStreams * 3);
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(nStreams - 1));
            s.parallel().forEach(i -> {
                try {
                    Thread.sleep(100);
                } catch (Exception ignore) {
                }
                System.out.print((System.currentTimeMillis() - start) / 100 + " ");
            });
        }
    }
}

   /*public static void main(String[] args) {
        if(args.length == 0)
            System.out.println("Illegal arguments count");
        System.out.println("Argument: "+args[0]);
        checkParallelStreams(4);
    }

    private static void checkSort() {
        final int SIZE = 10;

        Distance[] initialReferenceArray = new Distance[SIZE];
        Arrays.setAll(initialReferenceArray, (i) -> (new Distance()));
        Arrays.stream(initialReferenceArray).forEach((i) -> System.out.print(i + " "));
        Distance[] sortedReferenceArray = Arrays.stream(initialReferenceArray).sorted().toArray(Distance[]::new);
        System.out.println();
        Arrays.stream(sortedReferenceArray).forEach((i) -> System.out.print(i + " "));

        int[] initialIntArray = new int[SIZE];
        Arrays.setAll(initialIntArray, (i) -> (Integer.valueOf((int) (Math.random() * 100))));
        System.out.println();
        Arrays.stream(initialIntArray).forEach((i) -> System.out.print(i + " "));
        int[] sortedIntArray = Arrays.stream(initialIntArray).sorted().toArray();
        System.out.println();
        Arrays.stream(sortedIntArray).forEach((i) -> System.out.print(i + " "));

    }

    private static void checkParallelStreams(int nStreams) {
        //System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(nStreams - 1));
        IntStream s = IntStream.range(0, nStreams * 3);
        // -Djava.util.concurrent.ForkJoinPool.common.parallelism=5
        // System.out.print(ForkJoinPool.commonPool().getParallelism() + ": ");
        long start = System.currentTimeMillis();
        s.parallel().forEach(i -> {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            System.out.print((System.currentTimeMillis() - start) / 100 + " ");
        });
    }

    private static void checkParallelSort(int nStreams) {
        final int SIZE = 10;
        Integer[] initialArray = new Integer[SIZE];
        Arrays.setAll(initialArray, (i) -> (Integer.valueOf((int) (Math.random() * 100))));
        Arrays.stream(initialArray).forEach((i) -> System.out.print(i + " "));
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(nStreams - 1));
        Integer[] sortedArray = Arrays.stream(initialArray).parallel().sorted().toArray(Integer[]::new);
        System.out.println();
        Arrays.stream(sortedArray).forEach((i) -> System.out.print(i + " "));
    }*/
