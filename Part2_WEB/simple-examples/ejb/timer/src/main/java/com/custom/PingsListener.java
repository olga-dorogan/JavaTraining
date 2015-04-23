package com.custom;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by olga on 23.04.15.
 */
@Startup
@Singleton
public class PingsListener {
    final List<Ping> pings = new CopyOnWriteArrayList<>();

    public void listen(@Observes Ping ping) {
        System.out.println("ping: " + ping);
        pings.add(ping);
    }
}
