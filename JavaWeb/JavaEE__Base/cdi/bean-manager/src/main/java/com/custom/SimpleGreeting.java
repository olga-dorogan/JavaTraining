package com.custom;

/**
 * Created by olga on 06.04.15.
 */
public class SimpleGreeting implements Greeting {
    public String sayHello(String name) {
        return "hello, " + name;
    }
}
