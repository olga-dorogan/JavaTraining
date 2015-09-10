package com.custom.random;

import javax.enterprise.inject.Produces;
import java.util.Random;

/**
 * Created by olga on 06.04.15.
 */
public class RandomFactory {
    java.util.Random random = new Random(System.nanoTime());

    public Random getRandom(){
        return random;
    }
    @Produces
    @com.custom.random.Random
    public int getRandomNum() {
        return getRandom().nextInt(100);
    }
}
