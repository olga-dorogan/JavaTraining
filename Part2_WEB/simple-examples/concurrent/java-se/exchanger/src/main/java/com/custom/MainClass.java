package com.custom;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by olga on 23.04.15.
 */
public class MainClass {
    private static Exchanger<DataBuffer> exchanger = new Exchanger<DataBuffer>();
    private static DataBuffer initialEmptyDataBuffer = new DataBuffer();
    private static DataBuffer initialFullDataBuffer = new DataBuffer("ITEM");

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new EmptyingLoop());
        executorService.execute(new FillingLoop());
        executorService.shutdown();
    }

    private static class FillingLoop implements Runnable {

        public void run() {
            DataBuffer dataBuffer = initialEmptyDataBuffer;
            try {
                int cnt = 0;
                while (true) {
                    addToBuffer(dataBuffer, ++cnt);
                    if (dataBuffer.isFull()) {
                        System.out.println("...filling loop before exchange ...");
                        dataBuffer = exchanger.exchange(dataBuffer);
                        System.out.println("...filling loop after exchange ...");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void addToBuffer(DataBuffer dataBuffer, int cnt) throws InterruptedException {
            String item = "newItem" + cnt;
            System.out.println("Add to buffer item: " + item);
            dataBuffer.add(item);
            Thread.sleep(2*1000);
        }
    }

    private static class EmptyingLoop implements Runnable {

        public void run() {
            DataBuffer dataBuffer = initialFullDataBuffer;
            try {
                while (true) {
                    takeFromBuffer(dataBuffer);
                    if (dataBuffer.isEmpty()) {
                        System.out.println("..emptying loop before exchange...");
                        dataBuffer = exchanger.exchange(dataBuffer);
                        System.out.println("...emptying loop after exchange...");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void takeFromBuffer(DataBuffer dataBuffer) throws InterruptedException {
            System.out.println("Taked from buffer: " + dataBuffer.remove());
            Thread.sleep(2*1000);
        }
    }
}
