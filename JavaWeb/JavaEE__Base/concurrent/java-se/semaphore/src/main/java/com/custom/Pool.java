package com.custom;

import java.util.concurrent.Semaphore;

/**
 * Created by olga on 23.04.15.
 */
public class Pool {
    public static final int MAX_AVAILABLE = 10;
    private Semaphore semaphore;
    private String items[];
    private boolean used[];

    public Pool() {
        semaphore = new Semaphore(MAX_AVAILABLE, true);
        items = new String[MAX_AVAILABLE];
        used = new boolean[MAX_AVAILABLE];
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            items[i] = "ITEM " + i;
        }
    }

    public String getItem() throws InterruptedException {
        semaphore.acquire();
        return getNextAvailableItem();
    }

    public void putItem(String item) {
        if (markAsUnused(item)) {
            semaphore.release();
        }
    }

    private synchronized boolean markAsUnused(String item) {
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            if (item == items[i]) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                } else {
                    return false;
                }

            }
        }
        return false;
    }

    private synchronized String getNextAvailableItem() {
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            if (!used[i]) {
                used[i] = true;
                return items[i];
            }
        }
        return null;
    }
}
