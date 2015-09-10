package com.simple.bean;

import javax.ejb.Stateless;
import java.lang.Override;
import java.lang.String;
import com.simple.bean.Greeting;

/**
 * Created by olga on 08.04.15.
 */
@Stateless
public class GreetingImpl implements Greeting {
    @Override
    public String greet() {
        return "hello!";
    }
}
