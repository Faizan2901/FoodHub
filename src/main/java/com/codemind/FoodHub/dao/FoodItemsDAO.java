package com.codemind.FoodHub.dao;

import com.codemind.FoodHub.entity.FoodItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemsDAO extends JpaRepository<FoodItems,Integer> {
}
