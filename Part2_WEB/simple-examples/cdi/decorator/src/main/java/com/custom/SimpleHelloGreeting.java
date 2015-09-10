package com.custom;

/**
 * Created by olga on 10.04.15.
 */
public class SimpleHelloGreeting implements HelloGreeting {
    @Override
    public String greet() {
        return "hello";
    }
}
