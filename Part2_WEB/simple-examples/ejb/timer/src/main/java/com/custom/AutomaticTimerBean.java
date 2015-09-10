package com.custom;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by olga on 23.04.15.
 */
@Startup
@Singleton
public class AutomaticTimerBean {
    @Resource
    SessionContext ctx;
    @Inject
    Event<Ping> pingEvent;

    @Schedule (hour = "*", minute = "*", second = "*/10", persistent = false, info = "Automatic timer (every 10 sec)")
    public void printDate() {
        Collection<Timer> timers = ctx.getTimerService().getTimers();
        for (Timer timer : timers) {
            pingEvent.fire(new Ping(timer.getInfo().toString()));
        }
    }
}
