package com.custom.service;

import com.custom.entity.Shop;
import com.custom.exception.ShopNotFound;

import java.util.List;

/**
 * Created by olga on 14.05.15.
 */
public interface ShopService {
    public Shop create(Shop shop);
    public Shop delete(int id) throws ShopNotFound;
    public List<Shop> findAll();
    public Shop update(Shop shop) throws ShopNotFound;
    public Shop findById(int id);
}
