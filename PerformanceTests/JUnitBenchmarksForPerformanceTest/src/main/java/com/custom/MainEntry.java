package com.custom;

/**
 * Created by olga on 08.12.14.
 */
public class MainEntry {
    public static long recursive(int number){
        switch (checkValidNumber(number)){
            case 0: return number;
            default: return number*recursive(number-1);
        }
    }

    public static long iterative(int number){
        checkValidNumber(number);
        long result = 1;
        for(int i=1; i<number; i++)
            result *= i;
        return result;
    }

    private static int checkValidNumber(int number){
        if(number < 0)
            throw new IllegalArgumentException();
        return number;
    }
}
