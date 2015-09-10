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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by olga on 07.04.15.
 */
@RunWith(Arquillian.class)
public class GreetingTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(Greeting.class.getPackage())
                .addAsManifestResource("beans.xml");
    }

    @Inject
    private Greeting greeting;

    @Test
    public void test() {
        assertThat(greeting, is(notNullValue()));
        assertEquals("hello", greeting.getGreeting());
        greeting.setGreeting("hello");
        assertNotEquals("hello", greeting.getGreeting());
    }
}