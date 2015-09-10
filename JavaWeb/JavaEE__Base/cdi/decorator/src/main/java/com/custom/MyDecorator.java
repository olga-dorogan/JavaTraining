package com.custom;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

/**
 * Created by olga on 10.04.15.
 */
@Decorator
public class MyDecorator implements HelloGreeting{
    @Inject
    @Delegate
    @Any
    HelloGreeting greeting;

    @Override
    public String greet() {
        return greeting.greet() + " (decorator)";
    }
}
