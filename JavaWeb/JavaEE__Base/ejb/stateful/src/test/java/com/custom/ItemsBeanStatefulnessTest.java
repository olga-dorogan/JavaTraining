package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.*;

/**
 * Created by olga on 08.04.15.
 */
@RunWith(Arquillian.class)
public class ItemsBeanStatefulnessTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ItemsBean.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    @EJB
    private ItemsBean bean1;
    @EJB
    private ItemsBean bean2;

    @Test
    public void beans_should_not_be_the_same() {
        assertNotEquals(bean1, bean2);
    }

    @Test
    public void beans_should_not_be_connectd() {
        bean1.addItem("test item");
        assertFalse(bean1.getItems().isEmpty());
        assertTrue(bean2.getItems().isEmpty());
    }
}