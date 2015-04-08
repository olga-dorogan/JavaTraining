package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by olga on 08.04.15.
 */
@RunWith(Arquillian.class)
public class ItemsBeanTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ItemsBean.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private ItemsBean bean;

    @Test
    @InSequence(1)
    public void bean_should_be_injected() {
        assertThat(bean, is(notNullValue()));
    }

    @Test
    @InSequence(2)
    public void bean_should_add_item() {
        bean.addItem("test item");
        assertEquals(1, bean.getItems().size());
    }

    @Test
    @InSequence(3)
    public void bean_should_be_empty() {
        assertTrue(bean.getItems().isEmpty());
    }
}