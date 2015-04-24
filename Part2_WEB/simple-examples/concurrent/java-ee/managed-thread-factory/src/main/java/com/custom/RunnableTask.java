package com.custom;

/**
 * Created by olga on 24.04.15.
 */
public class RunnableTask implements Runnable {
    private int id;

    public RunnableTask() {
    }

    public RunnableTask(int id) {
        this();
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Simple runnable task " + id);
        try {
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
