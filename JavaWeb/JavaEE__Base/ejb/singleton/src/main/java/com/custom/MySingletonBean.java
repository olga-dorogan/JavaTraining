package com.custom;

import javax.ejb.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by olga on 10.04.15.
 */
@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MySingletonBean {
    @Lock(LockType.READ)
    public void act() {
        System.out.println("Entered MySingletonBean/act() on " + new Date().toString() +
                " . Singleton instance " + this.hashCode() + " Thread : " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MySingletonBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Exit MySingletonBean/act() on " + new Date().toString() +
                " . Singleton instance " + this.hashCode() + " Thread : " + Thread.currentThread().getName());

    }
}
