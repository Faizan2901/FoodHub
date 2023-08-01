package com.codemind.FoodHub.dao;

import com.codemind.FoodHub.entity.FoodItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodItemsDAO extends JpaRepository<FoodItems,Integer> {

    @Query (value="SELECT * FROM food_items  WHERE id=:foodId",nativeQuery = true)
    FoodItems findByFoodId(@Param("foodId") int foodId);

    FoodItems findByFoodName(String foodName);

    @Query(value = "SELECT * FROM food_items WHERE food_name=:foodName AND food_price=:foodPrice",nativeQuery = true)
    FoodItems findByFoodNameAndFoodPrice(@Param("foodName") String foodName,@Param("foodPrice") int foodPrice);

}
