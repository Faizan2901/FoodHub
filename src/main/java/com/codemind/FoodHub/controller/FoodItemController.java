package com.codemind.FoodHub.controller;

import com.codemind.FoodHub.dao.FoodItemsDAO;
import com.codemind.FoodHub.entity.FoodItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
