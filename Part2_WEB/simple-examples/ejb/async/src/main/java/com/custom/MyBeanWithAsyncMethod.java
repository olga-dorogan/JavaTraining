package com.custom;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by olga on 10.04.15.
 */
@Stateless
public class MyBeanWithAsyncMethod {
    public static final long AWAIT = 1*1000;

    @Asynchronous
    public Future<Integer> asyncAdd(int n1, int n2) {
        try {
            Thread.sleep(AWAIT);
        } catch (InterruptedException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
        }
        return new AsyncResult<>(n1 + n2);
    }
}
