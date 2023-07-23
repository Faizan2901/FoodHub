package com.codemind.FoodHub.controller;

import com.codemind.FoodHub.dao.FoodItemsDAO;
import com.codemind.FoodHub.dao.UserDAO;
import com.codemind.FoodHub.entity.FoodItems;
import com.codemind.FoodHub.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/food")
public class FoodItemController {

    @Autowired
    FoodItemsDAO foodItemsDAO;

    @Autowired
    AuthController authController;

    @Autowired
    UserDAO userDAO;

    @GetMapping("/foodItems")
    public String showMenu(Model model){

        List<FoodItems> foodItemsList=foodItemsDAO.findAll();
        model.addAttribute("foodItems",foodItemsList);

        return "default/food-items";

    }

    @GetMapping("/myOrder")
    public String showMyOrders(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Username : "+authentication.getName());

        return "default/food-items";

    }

    @PostMapping("/addThisFood")
    public String addFood(@RequestParam("foodId") String foodId,@RequestParam("quantity") String quantity){

        User user= userDAO.findByUserName(authController.getAuthenticateUserName());

        int fooId=Integer.parseInt(foodId);

        FoodItems foodItems=foodItemsDAO.findByFoodId(fooId);

        user.setFoodItems(user.add(foodItems));

        userDAO.save(user);



        return "default/food-items";
    }
}
