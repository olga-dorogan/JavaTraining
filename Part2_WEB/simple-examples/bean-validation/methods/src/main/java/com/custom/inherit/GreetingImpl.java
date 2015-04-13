package com.custom.inherit;

import javax.validation.constraints.NotNull;

/**
 * Created by olga on 11.04.15.
 */
public class GreetingImpl implements Greeting_ {
    @Override
    public String greet(@NotNull String name) {
        return "hello, " + name;
    }
}
