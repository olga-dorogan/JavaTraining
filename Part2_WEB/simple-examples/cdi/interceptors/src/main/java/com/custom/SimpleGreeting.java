package com.custom;

/**
 * Created by olga on 07.04.15.
 */
@MyInterceptorBinding
public class SimpleGreeting implements Greeting {
    private String greeting = "hello";
    @Override
    public String getGreeting() {
        return greeting;
    }

    @Override
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
