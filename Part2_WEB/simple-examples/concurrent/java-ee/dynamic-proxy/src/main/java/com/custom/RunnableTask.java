package com.custom;

import javax.enterprise.context.spi.Context;
import javax.naming.spi.InitialContextFactory;

/**
 * Created by olga on 24.04.15.
 */
public class RunnableTask implements Runnable {
//    @Inject
//    TestCDIBean testCDIBean;

    @Override
    public void run() {
        System.out.println("Runnable task is running");
//        System.out.println("bean said: " + testCDIBean.greeting());
    }
}
