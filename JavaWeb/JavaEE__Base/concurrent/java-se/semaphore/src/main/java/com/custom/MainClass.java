package com.custom;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by olga on 23.04.15.
 */
public class MainClass {
    public static void main(String[] args) {
        final Pool pool = new Pool();
        Runnable runnable = new Runnable() {
            public void run() {
                String threadName = Thread.currentThread().getName();
                try {
                    while (true) {
                        String item;
                        System.out.println(threadName + "acquires item " + (item = pool.getItem()));
                        Thread.sleep((long) (200 + (Math.random() * 100)));
                        System.out.println(threadName + "releases item " + item);
                        pool.putItem(item);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ExecutorService executorServices[] = new ExecutorService[Pool.MAX_AVAILABLE + 1];
        for (int i = 0; i < executorServices.length; i++) {
            executorServices[i] = Executors.newSingleThreadExecutor();
            executorServices[i].execute(runnable);
        }
    }
}
