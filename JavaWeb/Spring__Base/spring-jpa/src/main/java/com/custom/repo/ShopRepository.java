package com.custom.repo;

import com.custom.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olga on 14.05.15.
 */
public interface ShopRepository extends JpaRepository<Shop, Integer> {
}
