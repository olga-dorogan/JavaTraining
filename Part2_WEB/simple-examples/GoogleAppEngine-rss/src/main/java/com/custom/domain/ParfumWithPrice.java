package com.custom.domain;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by olga on 21.06.15.
 */
@Entity
@Cache
public class ParfumWithPrice {
    public static final String PRODUCER = "parfumHouse";
    public static final String PRICE = "price";
    @Id
    private String name;
    @Index
    private String parfumHouse;
    @Index
    private String price;

    public ParfumWithPrice() {

    }
    public ParfumWithPrice(String name, String parfumHouse, String price) {
        this.name = name;
        this.parfumHouse = parfumHouse;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParfumHouse() {
        return parfumHouse;
    }

    public void setParfumHouse(String parfumHouse) {
        this.parfumHouse = parfumHouse;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ParfumWithPrice {" +
                "name='" + name + '\'' +
                ", parfumHouse='" + parfumHouse + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
