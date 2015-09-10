package com.custom;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.BatchStatus;
import javax.inject.Named;

/**
 * Created by olga on 23.04.15.
 */
@Named
public class MyBatchlet extends AbstractBatchlet{

    @Override
    public String process() throws Exception {
        System.out.println("Inside batchlet");
        return BatchStatus.COMPLETED.toString();
    }
}
