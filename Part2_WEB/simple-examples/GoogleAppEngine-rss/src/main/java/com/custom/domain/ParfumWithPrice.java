package com.custom.domain;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.DoubleSummaryStatistics;

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
    private Double price;

    public ParfumWithPrice() {

    }
    public ParfumWithPrice(String name, String parfumHouse, Double price) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
