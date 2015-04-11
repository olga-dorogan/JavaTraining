package com.custom;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by olga on 11.04.15.
 */
@RequestScoped
public class MyBean1 {
    private MyParam param;

    public MyBean1() {
    }

    @Inject
    public MyBean1(@Valid MyParam param) {
        this.param = param;
    }

    public MyParam getParam() {
        return param;
    }
}
