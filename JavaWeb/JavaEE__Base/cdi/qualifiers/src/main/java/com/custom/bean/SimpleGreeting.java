package com.custom.bean;

import com.custom.Simple;

/**
 * Created by olga on 06.04.15.
 */
@Simple
public class SimpleGreeting implements Greeting {
    @Override
    public String sayHello() {
        return "Hello!";
    }
}
