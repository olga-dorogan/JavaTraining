package com.custom.num;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Created by olga on 06.04.15.
 */
@RunWith(Arquillian.class)
public class MaxNumFactoryTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MaxNumFactory.class.getPackage())
                .addAsManifestResource("beans.xml");
    }

    @Inject
    @MaxNum
    int maxNum;

    @Test
    public void should_maxNum_be_100() {
        assertEquals(100, maxNum);
    }
}