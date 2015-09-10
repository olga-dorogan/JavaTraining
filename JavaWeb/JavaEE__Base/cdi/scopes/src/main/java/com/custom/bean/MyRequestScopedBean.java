package com.custom.bean;

import javax.enterprise.context.RequestScoped;

/**
 * Created by olga on 06.04.15.
 */
@RequestScoped
public class MyRequestScopedBean {
    public String getID() {
        return this + "";
    }
}
