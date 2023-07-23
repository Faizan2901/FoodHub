package com.codemind.FoodHub.dao;

import com.codemind.FoodHub.entity.FoodItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodItemsDAO extends JpaRepository<FoodItems,Integer> {

    @Query (value="SELECT * FROM FoodItems f WHERE f.id=:foodId",nativeQuery = true)
    FoodItems findByFoodId(@Param("foodId") int foodId);
}
