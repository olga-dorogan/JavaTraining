package com.custom;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Created by olga on 23.04.15.
 */
//@Startup
@Singleton
public class ProgrammaticTimerBean {
    @Inject
    Event<Ping> pingEvent;

    @Resource
    TimerService timerService;

    @PostConstruct
    public void initialize() {
        ScheduleExpression scheduleExpression = new ScheduleExpression()
                .hour("*")
                .minute("*")
                .second("*/10");
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo("Programmatic timer (every 10 sec)");
        timerConfig.setPersistent(false);
        timerService.createCalendarTimer(scheduleExpression, timerConfig);
    }

    @Timeout
    public void timeout(Timer timer) {
        pingEvent.fire(new Ping(timer.getInfo().toString()));
    }
}
