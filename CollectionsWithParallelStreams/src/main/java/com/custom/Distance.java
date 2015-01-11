package com.custom;

/**
 * Created by olga on 08.01.15.
 */
public class Distance implements Comparable<Distance> {
    // variables to check how many times this class instantiates by the stream
    private static int count = 0;
    private final int id;

    private final int startPoint;
    private final int endPoint;

    public Distance() {
        this((int) (Math.random() * 100), (int) (Math.random() * 100));
    }

    public Distance(int startPoint, int endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        id = count;
        count++;
    }

    @Override
    public int compareTo(Distance o) {
        int thisLength = endPoint - startPoint;
        int otherLength = o.endPoint - o.startPoint;
        return (thisLength - otherLength);
    }

    @Override
    public String toString() {
        return "Distance " + id + "(length: " + (endPoint - startPoint) + ")";
    }
}
