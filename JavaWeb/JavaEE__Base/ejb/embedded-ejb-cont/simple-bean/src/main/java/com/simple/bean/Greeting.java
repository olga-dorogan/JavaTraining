package com.simple.bean;
import javax.ejb.Remote;

@Remote
public interface Greeting {
    public String greet();
}