package com.custom.bean;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

/**
 * Created by olga on 06.04.15.
 */
@ApplicationScoped
public class MyApplicationScopedBean implements Serializable {
    public String getID() {
        return this + "";
    }
}
