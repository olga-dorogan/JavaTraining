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

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by olga on 08.04.15.
 */
@RunWith(Arquillian.class)
public class AccountBeanStatelessnessTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(AccountBean.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    // !!!!!!!!!
    // annotations MUST BE @EJB, not @Inject
    // (if they are '@Inject', injected instances
    //  will not be the same --- they will not be pooled
    //  and every call of bean method will work with a new object)
    @EJB
    private AccountBean accountBean1;
    @EJB
    private AccountBean accountBean2;

    @Test
    public void test() {
        assertThat(accountBean1, is(notNullValue()));
        assertThat(accountBean2, is(notNullValue()));
        assertEquals(accountBean1, accountBean2);
    }
}