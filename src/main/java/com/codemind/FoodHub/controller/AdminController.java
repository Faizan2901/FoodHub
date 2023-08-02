package com.codemind.FoodHub.controller;

import com.codemind.FoodHub.dao.FoodItemsDAO;
import com.codemind.FoodHub.dao.UserDAO;
import com.codemind.FoodHub.entity.FoodItems;
import com.codemind.FoodHub.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AuthController authController;

    @Autowired
    UserDAO userDAO;

    @Autowired
    FoodItemsDAO foodItemsDAO;


    @GetMapping("/addFood")
    String addFoodItem(Model model){
        User user= userDAO.findByUserName(authController.getAuthenticateUserName());
        model.addAttribute("userDetails",user.getFirstName()+" "+user.getLastName());
        model.addAttribute("foodItem",new FoodItems());

        return "default/add-FoodItem";
    }

    @PostMapping("/save")
    String saveFoodItem(@ModelAttribute("foodItem") FoodItems tempFoodItems, Model model){

        FoodItems foodItems=foodItemsDAO.findByFoodNameAndFoodPrice(tempFoodItems.getFoodName(), tempFoodItems.getFoodPrice());

        if(foodItems!=null){
            model.addAttribute("error",true);
            return "default/add-FoodItem";
        }


        foodItemsDAO.save(tempFoodItems);


        return "redirect:/food/foodItems";
    }

    @GetMapping("/updateItem")
    String updateFoodItem(@RequestParam("foodId") String foodId,Model model){

        FoodItems foodItem=foodItemsDAO.findById(Integer.parseInt(foodId));

        model.addAttribute("foodItem",foodItem);

        return "default/add-FoodItem";

    }

    @GetMapping("/deleteItem")
    String deleteFoodItem(@RequestParam("foodId") String foodId){

        FoodItems foodItems=foodItemsDAO.findById(Integer.parseInt(foodId));

        foodItemsDAO.delete(foodItems);

        return "redirect:/food/foodItems";

    }


}
