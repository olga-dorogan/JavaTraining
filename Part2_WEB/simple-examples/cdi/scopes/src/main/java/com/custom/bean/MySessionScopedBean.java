package com.custom.bean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * Created by olga on 06.04.15.
 */
@SessionScoped
public class MySessionScopedBean implements Serializable{
    public String getID() {
        return this + "";
    }
}
