package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by olga on 07.04.15.
 */
@RunWith(Arquillian.class)
public class MyEventTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MyEvent.class.getPackage())
                .addAsManifestResource("beans.xml");
    }

    @Inject
    private MyEventSender sender;
    @Inject
    private MyEventReceiver receiver;

    private MyEvent event1 = new MyEvent("test event", 0);
    private MyEvent event2 = new MyEvent("sended event", 1);

    @Test
    public void test() {
        assertThat(sender, is(notNullValue()));
        assertThat(receiver, is(notNullValue()));
        receiver.setEvent(event1);
        assertEquals(event1, receiver.getEvent());
        sender.send(event2);
        assertEquals(event2, receiver.getEvent());
    }
}