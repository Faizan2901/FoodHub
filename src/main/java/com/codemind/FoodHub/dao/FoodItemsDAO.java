package com.codemind.FoodHub.dao;

import com.codemind.FoodHub.entity.FoodItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodItemsDAO extends JpaRepository<FoodItems,Integer> {

    @Query (value="SELECT * FROM food_items f WHERE f.id=:foodId",nativeQuery = true)
    FoodItems findByFoodId(@Param("foodId") int foodId);

    @Query(value = "SELECT COUNT(*) as dp_count FROM food_items_user_list fu GROUP BY user_list_id,food_items_id HAVING fu.user_list_id=:userId and fu.food_items_id=:foodId",nativeQuery = true)
    int countOfSameItemsofCustomer(@Param("userId") int userId,@Param("foodId") int foodId);

    @Query(value = "DELETE FROM food_items_user_list fu WHERE fu.user_list_id=:userId and fu.food_items_id=:foodId AND NOT IN(SELECT MIN(user_list_id),MIN(food_items_id) FROM fu GROUP BY user_list_id,food_items_id",nativeQuery = true)
    void removeRedundantItemForCustomer(@Param("userId") int userId,@Param("foodId") int foodId);



}
