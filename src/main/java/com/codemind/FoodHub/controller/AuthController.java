package com.codemind.FoodHub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthController {

    public String getAuthenticateUserName(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return authentication.getName();
        } else {
            return null;
        }


    }

}
