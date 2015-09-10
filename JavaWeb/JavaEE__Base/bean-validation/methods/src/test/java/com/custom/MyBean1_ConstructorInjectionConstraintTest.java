package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

/**
 * Created by olga on 11.04.15.
 */
@Ignore
@RunWith(Arquillian.class)
public class MyBean1_ConstructorInjectionConstraintTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(MyParam.class, MyBean1.class);
    }

    // for using of JUnit expected exception
    // have to use rules
    // because Arquillian makes proxies for test methods
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Inject
    MyBean1 bean;

    @Test
    public void testInjectBeanShouldThrowConstraintViolationException() throws Throwable {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("javax.validation.constraints.NotNull");
        thrown.expectMessage("MyBean1.arg0.value");
        bean.getParam();

    }

}