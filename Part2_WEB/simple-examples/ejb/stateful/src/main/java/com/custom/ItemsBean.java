package com.custom;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 08.04.15.
 */
@Stateful
public class ItemsBean {
    List<String> items;

    public ItemsBean() {
        items = new ArrayList<>();
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void remove(String item) {
        items.remove(item);
    }

    public List<String> getItems() {
        return items;
    }

    @Remove
    public void remove() {
        items= null;
    }
}
