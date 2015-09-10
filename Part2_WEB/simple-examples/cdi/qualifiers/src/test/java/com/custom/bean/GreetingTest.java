package com.custom.bean;

import com.custom.Simple;
import com.custom.Smiley;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by olga on 06.04.15.
 */
@RunWith(Arquillian.class)
public class GreetingTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(Greeting.class.getPackage())
                .addClasses(Simple.class, Smiley.class)
                .addAsManifestResource("beans.xml");
    }

    @Inject
    Greeting defaultGreeting;
    @Inject
    @Simple
    Greeting simpleGreeting;
    @Inject
    @Smiley
    Greeting smileyGreeting;

    @Test
    public void should_all_bean_be_injected() throws Exception {
        assertThat(defaultGreeting, is(notNullValue()));
        assertThat(defaultGreeting, instanceOf(DefaultGreeting.class));
        assertThat(simpleGreeting, is(notNullValue()));
        assertThat(simpleGreeting, instanceOf(SimpleGreeting.class));
        assertThat(smileyGreeting, is(notNullValue()));
        assertThat(smileyGreeting, instanceOf(SmileyGreeting.class));
    }
}