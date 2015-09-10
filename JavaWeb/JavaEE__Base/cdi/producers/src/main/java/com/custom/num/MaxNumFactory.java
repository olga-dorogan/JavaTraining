package com.custom.num;

import javax.enterprise.inject.Produces;

/**
 * Created by olga on 06.04.15.
 */
public class MaxNumFactory {
    private int num = 100;

    public void setNum(int num) {
        this.num = num;
    }

    @Produces
    @MaxNum
    public int getMaxNum() {
        return num;
    }
}
