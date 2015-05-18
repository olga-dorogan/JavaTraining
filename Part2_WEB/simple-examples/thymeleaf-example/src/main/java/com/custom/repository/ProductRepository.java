package com.custom.repository;

import com.custom.model.Product;

import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by olga on 18.05.15.
 */
@RequestScoped
public class ProductRepository {
    private static List<Product> productList;
    private static final int TESTED_CNT = 10;

    static {
        productList = Collections.synchronizedList(new ArrayList<Product>(TESTED_CNT));
        for (int i = 0; i < TESTED_CNT; i++) {
            productList.add(new Product("Product " + i, Math.random() * 100, i % 3 == 0));
        }
    }

    public List<Product> findAll() {
        return productList;
    }
}
