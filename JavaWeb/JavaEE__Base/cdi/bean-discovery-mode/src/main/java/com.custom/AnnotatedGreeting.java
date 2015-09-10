package com.custom;

import javax.enterprise.context.RequestScoped;

/**
 * Created by olga on 06.04.15.
 */
@RequestScoped
public class AnnotatedGreeting implements Greeting {
    public String sayHello(String name) {
        return "Hello, " + name + " (from annotated).";
    }
}
