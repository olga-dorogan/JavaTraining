package com.custom;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

/**
 * Created by olga on 09.04.15.
 */
@Stateless
public class MyStatelessBean {
    @MyInterceptorBinding
    public MyStatelessBean() {
        System.out.println("Stateless bean " + this + " constructor");
    }

    @PostConstruct
    private void postConstruct() {
        System.out.println("Stateless bean " + this + "  postConstruct");
    }

    @PreDestroy
    private void preDestroy() {
        System.out.println("Stateless bean " + this + "  preDestroy");
    }

    public void testMethod() {
        System.out.println("Stateless bean " + this + "  testMethod");
    }

}
