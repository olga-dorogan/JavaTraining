package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

/**
 * Created by olga on 09.04.15.
 */
@RunWith(Arquillian.class)
public class MyStatefulBeanTest {
    private static final String WEBAPP_SRC = "src/main/webapp";

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage(MyStatefulBean.class.getPackage());
        war.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                        .importDirectory(WEBAPP_SRC).as(GenericArchive.class),
                "/", Filters.includeAll());
        return war;
    }

    @EJB
    private MyStatefulBean statefulBean;
    @Test
    @InSequence(1)
    public void first_injection_stateful() {
        System.out.println(statefulBean);
    }

    @Test
    @InSequence(2)
    public void second_injection_stateful() {
        System.out.println(statefulBean);
    }
}