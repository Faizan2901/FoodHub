package com.codemind.FoodHub.controller;

import com.codemind.FoodHub.dao.UserDAO;
import com.codemind.FoodHub.entity.FoodItems;
import com.codemind.FoodHub.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DefaultController {

    @Autowired
    AuthController authController;

    @Autowired
    UserDAO userDAO;

    @GetMapping("/")
    public String getLandingPage(Model model){

        User user=userDAO.findByUserName(authController.getAuthenticateUserName());

        List<FoodItems> foodItemsList=user.getFoodItems();

        int count;

        for(FoodItems items:foodItemsList){
            System.out.println(items.getFoodName()+" "+items.getFoodPrice());
        }

        if(foodItemsList.size()==0){
            model.addAttribute("isAdded",false);

        }else{
            model.addAttribute("isAdded",true);
            model.addAttribute("foodItems",foodItemsList);
        }

        return "default/home";
    }
}
