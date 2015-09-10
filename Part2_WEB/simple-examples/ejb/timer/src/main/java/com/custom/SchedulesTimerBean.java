package com.custom;

import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Created by olga on 23.04.15.
 */
//@Startup
@Singleton
public class SchedulesTimerBean {
    @Inject
    Event<Ping> pingEvent;

    @Schedules({
            @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false, info = "every 5 sec timer"),
            @Schedule(hour = "*", minute = "*", second = "*/20",persistent = false, info = "every 20 sec timer")
    })
    public void automaticallyScheduled(Timer timer) {
        pingEvent.fire(new Ping((String) timer.getInfo()));

    }
}
