package com.custom;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by olga on 23.04.15.
 */
public class MainClass {
    private static final int N = 3;

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch startSignal = new CountDownLatch(1);
        final CountDownLatch stopSignal = new CountDownLatch(N);
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    startSignal.await();
                    System.out.println(Thread.currentThread().getName() + " is working ... ");
                    Thread.sleep((long) (200 + Math.random() * 100));
                    stopSignal.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ExecutorService executorServices[] = new ExecutorService[N];
        for (int i = 0; i < N; i++) {
            executorServices[i] = Executors.newSingleThreadExecutor();
            executorServices[i].execute(runnable);
            executorServices[i].shutdown();
        }
        System.out.println("... before threads running...");
        startSignal.countDown();
        System.out.println("... waiting all threads finish...");
        stopSignal.await();
        System.out.println("... after threads running");
    }

}
