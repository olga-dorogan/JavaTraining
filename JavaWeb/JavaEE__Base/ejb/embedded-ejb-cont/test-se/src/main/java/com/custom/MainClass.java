package com.custom;

import com.simple.bean.Greeting;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

/**
 * Created by olga on 08.04.15.
 */
public class MainClass {
    public static void main(String[] args) {
        EJBContainer container = null;
        try  {
            container = EJBContainer.createEJBContainer();
            Greeting greeting = (Greeting) container.getContext()
                    .lookup("java:global/simple-bean/GreetingImpl!com.simple.bean.Greeting");
            System.out.println("From greeting: " + greeting.greet());
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (container != null) {
                container.close();
            }
        }
        System.out.println("Hello, world");

    }
}
