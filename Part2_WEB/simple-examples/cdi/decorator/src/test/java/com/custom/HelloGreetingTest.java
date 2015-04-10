package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by olga on 10.04.15.
 */
@RunWith(Arquillian.class)
public class HelloGreetingTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HelloGreeting.class.getPackage())
                .addAsManifestResource(new File("src/main/webapp/WEB-INF/beans.xml"), "beans.xml");
    }

    @Inject
    private HelloGreeting greeting;

    @Test
    public void test() {
        assertEquals("hello (decorator)", greeting.greet());
    }
}