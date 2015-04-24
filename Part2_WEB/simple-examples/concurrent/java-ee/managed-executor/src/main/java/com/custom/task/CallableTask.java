package com.custom.task;

import java.util.concurrent.Callable;

/**
 * Created by olga on 24.04.15.
 */
public class CallableTask implements Callable<Integer> {
    private final int n;

    public CallableTask(int n) {
        this.n = n;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        return sum;
    }
}
