package com.custom;

import javax.ejb.Stateless;

/**
 * Created by olga on 08.04.15.
 */
@Stateless
public class AccountBean {
    private long amount = 0;

    public long put(int amount) {
        this.amount += amount;
        return this.amount;
    }

    public long take(int amount) {
        this.amount -= amount;
        return this.amount;
    }

    public long getAmount() {
        return amount;
    }
}
