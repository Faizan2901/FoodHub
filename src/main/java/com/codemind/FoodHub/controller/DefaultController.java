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
import java.util.Iterator;
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

        Map<String,Integer> billMap=new HashMap<>();

        boolean noOrder=true;

        if(foodItemsList.isEmpty()){
            noOrder=false;
        }
        model.addAttribute("noOrder",noOrder);

        for(FoodItems foodItems:foodItemsList){
            String foodName=foodItems.getFoodName();
            int foodPrice=foodItems.getFoodPrice();

            if (billMap.containsKey(foodName))
            {
                int mapValue = billMap.get(foodName);
                billMap.put(foodName, mapValue + foodPrice);
            } else
            {
                billMap.put(foodName, foodPrice);
            }
        }

        Iterator<String> it = billMap.keySet().iterator();

        Map<String,Integer> finalMap=new HashMap<>();

        while(it.hasNext()){
            String foodName=it.next();
            FoodItems items=foodItemsDAO.findByFoodName(foodName);
            int foodPrice=items.getFoodPrice();
            int quantity=billMap.get(foodName)/foodPrice;
            finalMap.put(foodName,quantity);
        }

        if(foodItemsList.size()==0){
            model.addAttribute("isAdded",false);

        }else{
            model.addAttribute("isAdded",true);
            model.addAttribute("billMap",billMap);
            model.addAttribute("finalMap",finalMap);
        }

        System.out.println(billMap);
        System.out.println(finalMap);
        model.addAttribute("userDetails",user.getFirstName()+" "+user.getLastName());

        System.out.println(user.getFirstName()+" "+user.getLastName());

        return "default/home";
    }

}
