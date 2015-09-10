package com.custom.timer;

import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import java.util.Properties;

/**
 * Created by olga on 23.04.15.
 */
@Startup
@Singleton
public class MyTimerScheduleBean {
    //    persistent value is used to persist scheduled event when server crushes
    //    if it's true (as by default) after servers restart all events are fired as packet
    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
    public void startJob(Timer timer) {
        BatchRuntime.getJobOperator().start("myJob", new Properties());
    }
}
