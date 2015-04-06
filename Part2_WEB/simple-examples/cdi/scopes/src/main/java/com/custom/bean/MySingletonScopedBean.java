package com.custom.bean;


import javax.inject.Singleton;

/**
 * Created by olga on 06.04.15.
 */
@Singleton
public class MySingletonScopedBean {
    public String getID() {
        return this + "";
    }
}
