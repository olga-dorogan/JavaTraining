package com.custom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 23.04.15.
 */
public class DataBuffer {
    private static final int MAX_CNT = 3;
    private List<String> items;

    public DataBuffer() {
        items = new ArrayList<String>(MAX_CNT);
    }

    public DataBuffer(String prefix) {
        this();
        for (int i = 0; i < MAX_CNT; i++) {
            items.add(prefix + i);
        }
    }

    public String remove() {
        if (isEmpty()) {
            return null;
        }
        return items.remove(0);
    }

    public void add(String item) {
        if (!isFull()) {
            items.add(item);
        }
    }

    public boolean isFull() {
        if (items.size() < MAX_CNT) {
            return false;
        }
        return true;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
