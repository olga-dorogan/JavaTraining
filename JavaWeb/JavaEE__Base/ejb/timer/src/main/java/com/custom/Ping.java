package com.custom;

/**
 * Created by olga on 23.04.15.
 */
public class Ping {
    private String timeInfo;
    private long time = System.currentTimeMillis();

    public Ping(String timeInfo) {
        this.timeInfo = timeInfo;
    }

    @Override
    public String toString() {
        return "Ping{" +
                "timeInfo='" + timeInfo + '\'' +
                ", time=" + time +
                '}';
    }
}
