package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.jayway.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
/**
 * Created by olga on 10.04.15.
 */
@RunWith(Arquillian.class)
public class MyBeanWithAsyncMethodTest {
    @Deployment
    public static Archive<?> deploy() {
        File[] jars = Maven.resolver().loadPomFromFile("pom.xml")
                .resolve("com.jayway.awaitility:awaitility")
                .withTransitivity().asFile();
        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(jars)
                .addClass(MyBeanWithAsyncMethod.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private MyBeanWithAsyncMethod bean;

    @Test
    public void testAsyncAdd() throws Exception {
        assertNotNull(bean);
        int numberOne = 5;
        int numberTwo = 3;
        long startTime = System.currentTimeMillis();
        final Future<Integer> integerFuture = bean.asyncAdd(numberOne, numberTwo);
        assertThat(System.currentTimeMillis() - startTime, is(lessThan(MyBeanWithAsyncMethod.AWAIT)));
//        while (!integerFuture.isDone()) {
//            continue;
//        }

        await().until(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return integerFuture.isDone();
            }
        });
        assertThat(System.currentTimeMillis() - startTime, not(lessThan(MyBeanWithAsyncMethod.AWAIT)));
        assertEquals(numberOne + numberTwo, (long) integerFuture.get());
    }
}