package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Constructor;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by olga on 11.04.15.
 */
@Ignore
@RunWith(Arquillian.class)
public class MyBean1_ConstructorParametersViolationsTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(MyParam.class, MyBean1.class);
    }

    @Inject
    Validator validator;


    @Test
    public void testConstructorViolationsWhenNullParameter() throws NoSuchMethodException {
        MyParam param = new MyParam();

        ExecutableValidator constructorValidator = validator.forExecutables();
        Constructor<MyBean1> constructor = MyBean1.class.getConstructor(param.getClass());

        Set<ConstraintViolation<MyBean1>> constraints = constructorValidator.validateConstructorParameters(constructor,
                new Object[]{param});
        assertEquals(1, constraints.size());
        ConstraintViolation<MyBean1> violation = constraints.iterator().next();
        assertEquals("{javax.validation.constraints.NotNull.message}", violation.getMessageTemplate());
        assertEquals("MyBean1.arg0.value", violation.getPropertyPath().toString());
    }
    @Test
    public void testConstructorViolationsWhenNotNullParameter() throws NoSuchMethodException {
        MyParam param = new MyParam();
        param.setValue("test");

        ExecutableValidator constructorValidator = validator.forExecutables();
        Constructor<MyBean1> constructor = MyBean1.class.getConstructor(param.getClass());

        Set<ConstraintViolation<MyBean1>> constraints = constructorValidator.validateConstructorParameters(constructor,
                new Object[]{param});
        assertEquals(0, constraints.size());
    }
}