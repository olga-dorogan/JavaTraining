package com.custom.greetings;

/**
 * Created by olga on 06.04.15.
 */
public class HiGreeting implements Greeting {
    @Override
    public String sayHello() {
        return "Hi!";
    }
}
