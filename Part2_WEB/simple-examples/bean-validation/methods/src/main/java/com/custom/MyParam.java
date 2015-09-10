package com.custom;

import javax.enterprise.context.Dependent;
import javax.validation.constraints.NotNull;

/**
 * Created by olga on 11.04.15.
 */
@Dependent
public class MyParam {
    @NotNull
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
