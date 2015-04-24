package com.custom.executor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.concurrent.ManagedThreadFactory;
import java.util.concurrent.*;

/**
 * Created by olga on 24.04.15.
 */
@Singleton
public class PoolExecutorBean {
    @Resource
    ManagedThreadFactory threadFactory;

    private ExecutorService poolExecutor;
    private int corePoolSize = 3;
    private int maximumPoolSize = 5;
    private long keepAliveTime = 1;
    private int queueCapacity = 1;

    @PostConstruct
    public void init() {
        poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueCapacity));
    }

    @PreDestroy
    public void destroy() {
        poolExecutor.shutdown();
    }

    public ExecutorService getPoolExecutor() {
        return poolExecutor;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

}
