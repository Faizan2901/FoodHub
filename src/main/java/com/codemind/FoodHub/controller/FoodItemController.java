package com.codemind.FoodHub.controller;

import com.codemind.FoodHub.dao.FoodItemsDAO;
import com.codemind.FoodHub.entity.FoodItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/food")
public class FoodItemController {

    @Autowired
    FoodItemsDAO foodItemsDAO;

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

    @GetMapping("/addThisFood")
    public String addFood(@RequestParam("foodId") int fId){
        System.out.println(fId);

        return "default/food-items";
    }
}
