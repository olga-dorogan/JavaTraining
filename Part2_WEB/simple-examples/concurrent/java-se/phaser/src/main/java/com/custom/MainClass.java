package com.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

/**
 * Created by olga on 23.04.15.
 */
public class MainClass {
    public static void main(String[] args) {
        List<Runnable> tasks = new ArrayList<Runnable>();
        tasks.add(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + " running at " + System.currentTimeMillis());
            }
        });
        tasks.add(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + " running at " + System.currentTimeMillis());
            }
        });
        runTasks(tasks);
    }

    private static void runTasks(List<Runnable> tasks) {
        final Phaser phaser = new Phaser(1);
        for (final Runnable task : tasks) {
            phaser.register();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep((long) (200 + Math.random() * 300));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    phaser.arriveAndAwaitAdvance();
                    phaser.arrive();
                            task.run();
                }
            }).start();
        }
        phaser.arriveAndDeregister();
    }
}
