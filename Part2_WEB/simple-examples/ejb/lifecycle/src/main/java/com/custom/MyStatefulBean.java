package com.custom;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 09.04.15.
 */
//@Dependent
@RequestScoped
@MyInterceptorBinding
@Stateful
public class MyStatefulBean implements Serializable{
    private List<String> list;

    public MyStatefulBean() {
        System.out.println("MyStatefulBean " + this + " constructor");
    }

    @PostConstruct
    private void postConstruct() {
        list = new ArrayList<>();
        System.out.println("MyStatefulBean " + this + " postConstruct");
    }

    @PreDestroy
    private void preDestroy() {
        System.out.println("MyStatefulBean " + this + " preDestroy");
    }

    @PrePassivate
    private void prePassivate() {
        System.out.println("MyStatefulBean " + this + " prePassivate");
    }

    @PostActivate
    private void postActivate() {
        System.out.println("MyStatefulBean " + this + " postActivate");
    }

    public void addItem(String item) {
        list.add(item);
        System.out.println("MyStatefulBean " + this + " addItem");
    }

    public void removeItem(String item) {
        list.remove(item);
        System.out.println("MyStatefulBean " + this + " removeItem");
    }

    public List<String> items() {
        return list;
    }

    @Remove
    public void remove() {
        list = null;
    }
}

