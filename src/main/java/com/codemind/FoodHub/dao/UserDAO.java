package com.codemind.FoodHub.dao;

import com.codemind.FoodHub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Integer> {

    User findByUserName(String userName);
}
