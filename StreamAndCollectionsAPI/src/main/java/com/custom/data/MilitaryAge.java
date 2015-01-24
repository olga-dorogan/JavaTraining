package com.custom.data;

/**
 * Created by olga on 23.01.15.
 */
public class MilitaryAge {
    public static final int LOW_AGE = 20;
    public static final int HIGH_AGE = 27;

    public static boolean isMilitaryAge(int age) {
        if ((age >= LOW_AGE) && (age <= HIGH_AGE)) {
            return true;
        }
        return false;
    }
}
