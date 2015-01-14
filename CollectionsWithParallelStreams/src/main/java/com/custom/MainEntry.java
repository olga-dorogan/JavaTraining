package com.custom;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by olga on 07.01.15.
 */
public class MainEntry {

    private static final int
            INIT_ARRAY_SIZE = (1 << 7),
            RATE_ARRAY_SIZE = 2,
            FINAL_ARRAY_SIZE = (1 << 20);

    private static final int ROUNDS_FOR_AVERAGE_TIME = 3;
    private static final int ROUNDS_FOR_PREWARM = 0;

    private static final String fileForTestedArray = "tested_data" + FINAL_ARRAY_SIZE + ".dat";

    private static String fileName;
    private static int nStreams;

    private static enum ProgramToPlot {Excel, GnuPlot, Undefined}

    ;
    private static ProgramToPlot mode = ProgramToPlot.Undefined;

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("You must pass three arguments: number of streams, the name of program you will use " +
                    "and the name of the output file.");
            return;
        }
        if (args[1].equalsIgnoreCase("excel"))
            mode = ProgramToPlot.Excel;
        else if (args[1].equalsIgnoreCase("gnuplot"))
            mode = ProgramToPlot.GnuPlot;

        if (mode == ProgramToPlot.Undefined) {
            System.out.println("The name of program (second command line argument) must be 'excel' or 'gnuplot' " +
                    "(in any case).");
            return;
        }
        nStreams = Integer.parseInt(args[0]);
        fileName = args[2];


        // If it's first JVM call, generate tested data
        if (nStreams == 1) {
            Experiment.generateTestedData();
        }

        switch (mode) {
            case Excel:
                if (nStreams == 1) {
                    try (FileWriter writer = new FileWriter(fileName + ".csv")) {
                        writer.write(";");
                        for (int size = INIT_ARRAY_SIZE; size <= FINAL_ARRAY_SIZE; size *= RATE_ARRAY_SIZE) {
                            writer.write(String.valueOf(size));
                            writer.write(';');
                        }
                        writer.write('\n');
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                FullExperimentWithResultForExcel.doExperiment();
                break;
            case GnuPlot:
                if (nStreams == 1) {
                    FullExperimentWithResultForGnuPlot.createGraphFileForGnuPlot();
                    new File(fileName + ".txt").delete();
                }
                FullExperimentWithResultForGnuPlot.doExperiment();
                break;
        }
    }


    private static class FullExperimentWithResultForExcel {
        private static void doExperiment() {
            long time;
            try (FileWriter writer = new FileWriter(fileName + ".csv", true)) {
                writer.write(String.valueOf(nStreams));
                writer.write(';');
                for (int size = INIT_ARRAY_SIZE; size <= FINAL_ARRAY_SIZE; size *= RATE_ARRAY_SIZE) {
                    time = Experiment.getAvgTimeForParallelSort(size);
                    writer.write(String.valueOf(time/1e6) + ";");
                }
                writer.write('\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class FullExperimentWithResultForGnuPlot {
        /**
         * Fills the data file by times of the sort of the arrays with different sizes
         * and specified number of streams, in which the sort is processed.
         */
        private static void doExperiment() {
            StringBuilder sb = new StringBuilder();
            long time;
            try (FileWriter writer = new FileWriter(fileName + ".txt", true)) {
                for (int size = INIT_ARRAY_SIZE; size <= FINAL_ARRAY_SIZE; size *= RATE_ARRAY_SIZE) {
                    // get duration of the sort
                    time = Experiment.getAvgTimeForParallelSort(size);
                    // write the duration to the file
                    sb.append(size).append("\t").append(nStreams).append("\t").append(time).append('\n');
                    writer.write(sb.toString());
                    sb.setLength(0);
                    writer.write('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Creates the file in the specified format. The file can be used as input data for GnuPlot.
         */
        private static void createGraphFileForGnuPlot() {
            File dataFile = new File(fileName + ".graph");
            try (FileWriter writer = new FileWriter(dataFile)) {
                writer.write("#! /usr/bin/gnuplot -persist\n" +
                        "set terminal jpeg size 640, 480\n" +
                        "set output \"" + fileName + ".jpg\"\n" +
                        "set logscale y\n" +
                        "set logscale x 2\n" +
                        "set xlabel \"Streams count\"\n" +
                        "set ylabel \"Array size\"\n" +
                        "set zlabel \"Time (ms)\" rotate by 90\n" +
                        "set grid ytics lc rgb \"#bbbbbb\" lw 1 lt 0\n" +
                        "set grid xtics lc rgb \"#bbbbbb\" lw 1 lt 0\n" +
                        "set grid ztics lc rgb \"#bbbbbb\" lw 1 lt 0\n" +
                        "set dgrid3d\n" +
                        "set pm3d corners2color c2\n" +
                        "splot \"" + fileName + ".txt\" using 2:1:($3/1e6) with pm3d notitle");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static class Experiment {

        private static Integer[] getTestedData(int size) {
            Integer[] data = new Integer[size];
            try (DataInputStream in = new DataInputStream(new FileInputStream(fileForTestedArray))) {
                for (int i = 0; i < size; i++)
                    data[i] = in.readInt();
            } catch (IOException e) {
                System.out.println("Internal error with file reading.");
            }
            return data;
        }

        private static void generateTestedData() {
            if (new File(fileForTestedArray).isFile())
                return;
            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(fileForTestedArray))) {
                Random rnd = new Random(FINAL_ARRAY_SIZE);
                for (int i = 0; i < FINAL_ARRAY_SIZE; i++) {
                    out.writeInt(rnd.nextInt());
                }
            } catch (IOException e) {
                System.out.println("Internal error with tested data.");
            }
        }

        private static void preWarm(int size) {
            long time = 0;
            for (int r = 0; r < ROUNDS_FOR_PREWARM; r++) {
                time |= getTimeForParallelSort(size);
            }
            System.gc();
            if (time < 0) System.out.println();
        }

        private static long getAvgTimeForParallelSort(int arraySize) {
            Experiment.preWarm(arraySize);
            double sum = 0;
            for (int i = 0; i < ROUNDS_FOR_AVERAGE_TIME; i++) {
                sum += getTimeForParallelSort(arraySize);
            }
            return (long) (sum / ROUNDS_FOR_AVERAGE_TIME);
        }

        private static long getTimeForParallelSort(int arraySize) {
            Integer[] testedData = Experiment.getTestedData(arraySize);
            long time;
            if (nStreams == 1) {
                time = System.nanoTime();
                Arrays.sort(testedData);
                time = System.nanoTime() - time;
            } else {
                time = System.nanoTime();
                Arrays.parallelSort(testedData);
                time = System.nanoTime() - time;
            }
            return time;
        }
    }
}
