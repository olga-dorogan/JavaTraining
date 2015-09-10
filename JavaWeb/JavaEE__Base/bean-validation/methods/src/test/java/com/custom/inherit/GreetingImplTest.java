package com.custom.inherit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

/**
 * Created by olga on 11.04.15.
 */
@RunWith(Arquillian.class)
public class GreetingImplTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(GreetingImpl.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Inject
    Validator validator;

    @Inject
    Greeting_ greeting;

    @Test
    public void greetWhenNullParameter() {
        thrown.expect(ConstraintViolationException.class);
        greeting.greet(null);
    }
}