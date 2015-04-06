package com.custom.bean;

import com.custom.Smiley;

/**
 * Created by olga on 06.04.15.
 */
@Smiley
public class SmileyGreeting implements Greeting {
    @Override
    public String sayHello() {
        return "Hello :))";
    }
}
