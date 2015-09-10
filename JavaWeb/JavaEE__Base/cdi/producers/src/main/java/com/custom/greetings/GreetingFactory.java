package com.custom.greetings;

import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;

/**
 * Created by olga on 06.04.15.
 */
public class GreetingFactory {

    @Produces
    @Greetings(value = GreetingType.HELLO)
    public Greeting getGreeting(@New HelloGreeting helloGreeting) {
        System.out.println("Factory: hello");
        return helloGreeting;
    }
    @Produces
    @Greetings(value = GreetingType.HI)
    public Greeting getGreeting(@New HiGreeting hiGreeting) {
        return hiGreeting;
    }

    @Produces
    public Greeting getGreeting(@New DefaultGreeting defaultGreeting) {
        return defaultGreeting;
    }
}
