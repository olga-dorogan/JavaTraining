package com.custom;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by olga on 23.04.15.
 */
public class MainClass {
    private static final int PARTS_CNT = 3;

    public static void main(String[] args) {
        Runnable barrierRunnable = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + " calls barrier action");
            }
        };
        final CyclicBarrier barrier = new CyclicBarrier(PARTS_CNT, barrierRunnable);
        Runnable partRunnable = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + " before barrier ...");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " after barrier");
            }
        };
        ExecutorService executorServices[] = new ExecutorService[PARTS_CNT];
        for (int i = 0; i < PARTS_CNT; i++) {
            executorServices[i] = Executors.newSingleThreadExecutor();
            executorServices[i].execute(partRunnable);
            executorServices[i].shutdown();
        }
    }
}
