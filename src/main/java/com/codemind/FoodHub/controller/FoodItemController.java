package com.codemind.FoodHub.controller;

import com.codemind.FoodHub.dao.FoodItemsDAO;
import com.codemind.FoodHub.dao.UserDAO;
import com.codemind.FoodHub.entity.FoodItems;
import com.codemind.FoodHub.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    public String showMyOrders(Model model){

        String username = authController.getAuthenticateUserName();

        System.out.println("Username : "+username);

        User user=userDAO.findByUserName(username);

        Map<String,Integer> billMap=new HashMap<>();

        List<FoodItems> foodItemsList=user.getFoodItems();

        int totalBillPrice=0;

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

        System.out.println(billMap);

        Iterator<String> it = billMap.keySet().iterator();

        Map<String,Integer> finalMap=new HashMap<>();

        while(it.hasNext()){
            String foodName=it.next();
            FoodItems items=foodItemsDAO.findByFoodName(foodName);
            int foodPrice=items.getFoodPrice();
            int quantity=billMap.get(foodName)/foodPrice;
            totalBillPrice=totalBillPrice+(quantity*foodPrice);
            finalMap.put(foodName,quantity);
        }

        System.out.println(finalMap);
        System.out.println("Total Bill is : "+totalBillPrice);

        model.addAttribute("billMap",billMap);
        model.addAttribute("finalMap",finalMap);
        model.addAttribute("totalBillPrice",totalBillPrice);

        return "default/bill-page";

    }

    @PostMapping("/addThisFood")
    public String addFood(@RequestParam("foodId") String foodId){

        User user= userDAO.findByUserName(authController.getAuthenticateUserName());

        int fooId=Integer.parseInt(foodId);

        FoodItems foodItems=foodItemsDAO.findByFoodId(fooId);

        user.setFoodItems(user.add(foodItems));

        userDAO.save(user);



        return "redirect:/";
    }

    @PostMapping("/removeThisItem")
    public String removeUserItem(@RequestParam("foodName") String foodName){

        User user= userDAO.findByUserName(authController.getAuthenticateUserName());


        FoodItems deleteItem=foodItemsDAO.findByFoodName(foodName);

        List<FoodItems> foodItemsList=user.getFoodItems();

        foodItemsList.remove(deleteItem);
        user.setFoodItems(foodItemsList);

        userDAO.save(user);

        return "redirect:/";

    }

    @GetMapping("/addFood")
    String addFoodItem(Model model){
        User user= userDAO.findByUserName(authController.getAuthenticateUserName());
        model.addAttribute("userDetails",user.getFirstName()+" "+user.getLastName());
        model.addAttribute("foodItem",new FoodItems());

        return "default/add-FoodItem";
    }

    @PostMapping("/save")
    String saveFoodItem(@ModelAttribute("foodItem") FoodItems tempFoodItems,Model model){

        FoodItems foodItems=foodItemsDAO.findByFoodName(tempFoodItems.getFoodName());

        if(foodItems!=null){
            model.addAttribute("error",true);
            return "default/add-FoodItem";
        }


        foodItemsDAO.save(tempFoodItems);


        return "redirect:/food/foodItems";
    }
}
