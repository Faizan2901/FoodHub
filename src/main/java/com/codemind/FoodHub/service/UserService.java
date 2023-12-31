package com.codemind.FoodHub.service;

import com.codemind.FoodHub.entity.User;
import com.codemind.FoodHub.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    User findByUserName(String userName);


    void save(WebUser webUser);

}
