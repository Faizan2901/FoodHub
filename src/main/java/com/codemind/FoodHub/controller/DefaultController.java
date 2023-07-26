package com.codemind.FoodHub.controller;

import com.codemind.FoodHub.dao.FoodItemsDAO;
import com.codemind.FoodHub.dao.UserDAO;
import com.codemind.FoodHub.entity.FoodItems;
import com.codemind.FoodHub.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DefaultController {

    @Autowired
    AuthController authController;

    @Autowired
    UserDAO userDAO;

    @Autowired
    FoodItemsDAO foodItemsDAO;

    @GetMapping("/")
    public String getLandingPage(Model model){

        User user=userDAO.findByUserName(authController.getAuthenticateUserName());

        List<FoodItems> foodItemsList=user.getFoodItems();

        Map<Integer,Integer> countItems=new HashMap<>();


        for(FoodItems items:foodItemsList){
            System.out.println(items.getFoodName()+" "+items.getFoodPrice());
            countItems.put(items.getId(), foodItemsDAO.countOfSameItemsofCustomer(user.getId(), items.getId()));
        }

        if(foodItemsList.size()==0){
            model.addAttribute("isAdded",false);

        }else{
            model.addAttribute("isAdded",true);
            model.addAttribute("foodItems",foodItemsList);
            model.addAttribute("countItem",countItems);
            model.addAttribute("userDetails",user.getFirstName()+" "+user.getLastName());
        }

        return "default/home";
    }

}
