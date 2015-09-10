package com.custom.task;

/**
 * Created by olga on 23.04.15.
 */
public class RunnableTask implements Runnable {
    public void run() {
        System.out.println("Simple task is running " + System.currentTimeMillis());
    }
}
