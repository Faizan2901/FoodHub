package com.codemind.FoodHub.dao;

import com.codemind.FoodHub.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role,Integer> {

    Role findByName(String name);
}
