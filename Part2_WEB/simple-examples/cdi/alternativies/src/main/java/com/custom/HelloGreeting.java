package com.custom;

import javax.enterprise.inject.Alternative;

/**
 * Created by olga on 07.04.15.
 */
@Alternative
public class HelloGreeting implements Greeting {
    @Override
    public String greet() {
        return "hello";
    }
}
