package com.custom;

import javax.enterprise.context.Dependent;

/**
 * Created by olga on 24.04.15.
 */
@Dependent
public class TestCDIBean {
    public String greeting() {
        return "hello";
    }
}
