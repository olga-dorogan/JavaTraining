package com.custom;

import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Created by olga on 07.04.15.
 */
public class MyEventSender {
    @Inject
    private Event<MyEvent> event;

    public void send(MyEvent eventToSend) {
        event.fire(eventToSend);
    }
}
