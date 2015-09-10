package com.custom.domain;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by olga on 21.06.15.
 */
@Entity
@Cache
public class Counter {
    @Id
    String userId;
    private int cnt;

    public Counter() {
    }

    public Counter(String userId, int cnt) {
        this.userId = userId;
        this.cnt = cnt;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
