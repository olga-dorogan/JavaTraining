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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by olga on 08.04.15.
 */
@RunWith(Arquillian.class)
public class AccountBeanTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(AccountBean.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private AccountBean accountBean;

    @Test
    public void test() {
        assertThat(accountBean, is(notNullValue()));

        int amount = 5;
        long newValue = accountBean.put(amount);
        assertEquals(amount, newValue);
        // every call of ejb works with a new ejb instance
        // so, if initial value of AccountBean's account is 0 and
        // after subtracting a value from it, it will be negative
        newValue = accountBean.take(amount);
        assertEquals(-1 * amount, newValue);
    }
}