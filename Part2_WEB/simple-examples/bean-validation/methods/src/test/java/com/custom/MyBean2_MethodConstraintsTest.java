package com.custom;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by olga on 11.04.15.
 */
@RunWith(Arquillian.class)
public class MyBean2_MethodConstraintsTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(MyBean2.class);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Inject
    MyBean2 bean;

    @Test
    public void testParameterWhenNull() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("javax.validation.constraints.NotNull");
        bean.testString(null);
    }
    @Test
    public void methodSizeTooLong() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("javax.validation.constraints.Size");
        bean.testString("test");
    }
    @Test
    public void methodSizeOk() {
        bean.testString("tst");
    }

    @Test
    public void getDateFromPast() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("javax.validation.constraints.Future");
        bean.getDate(false);
    }

    @Test
    public void getDateFromFuture() {
        bean.getDate(true);
    }

    @Test
    public void showListWithEmptyList() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("javax.validation.constraints.Size");
        bean.showList(new ArrayList<String>(), "test");
    }

    @Test
    public void showListWithTooBigList() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("javax.validation.constraints.Size");
        List<String> list = new ArrayList<>(Arrays.asList(new String[]{"a", "b", "c", "d"}));
        bean.showList(list, "test");
    }

    @Test
    public void showListWithCorrectList() {
        List<String> list = new ArrayList<>(Arrays.asList(new String[]{"a", "b", "c"}));
        bean.showList(list, "test");
    }
}