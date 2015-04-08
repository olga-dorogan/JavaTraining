package com.custom;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import java.io.Serializable;

/**
 * Created by olga on 07.04.15.
 */
@SessionScoped
public class MyEventReceiver implements Serializable {
    private MyEvent event;

    public MyEventReceiver() {
        event = new MyEvent();
        event.setData("initial data");
        event.setTime(0);
    }

    public MyEvent getEvent() {
        return event;
    }

    public void setEvent(MyEvent event) {
        this.event = event;
    }

    public void receive(@Observes MyEvent eventReceived) {
        event = eventReceived;
    }

}
