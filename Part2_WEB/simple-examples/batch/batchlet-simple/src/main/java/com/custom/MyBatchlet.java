package com.custom;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.BatchStatus;
import javax.inject.Named;

/**
 * Created by olga on 22.04.15.
 */
@Named
public class MyBatchlet extends AbstractBatchlet {
    private static final long DELAY_MS = 1 * 1000;

    @Override
    public String process() throws Exception {
        System.out.println("Long runnning operation in the batchlet...");
        System.out.println("--- Batchlet operation start");
        Thread.sleep(DELAY_MS);
        System.out.println("--- Batchlet operation end");
        return BatchStatus.COMPLETED.toString();
    }
}
