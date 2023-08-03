package com.codemind.FoodHub.controller;

import com.codemind.FoodHub.dao.FoodItemsDAO;
import com.codemind.FoodHub.dao.UserDAO;
import com.codemind.FoodHub.entity.FoodItems;
import com.codemind.FoodHub.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping("/users")
    String getUsers(Model model){

        List<User> userList=userDAO.findAll();

        model.addAttribute("users",userList);

        return "default/user-list";
    }


    @GetMapping("/deleteUser")
    String deleteUser(@RequestParam("userId") String userId){

        Optional<User> user=userDAO.findById(Integer.parseInt(userId));

        if(user.isPresent()){
            user.get().getRoles().clear();
            userDAO.deleteById(Integer.parseInt(userId));
        }

        return "redirect:/admin/users";

    }

    @GetMapping("/checkOrder")
    String checkUserOrder(@RequestParam("userId") String userId,Model model){

        Optional<User> user=userDAO.findById(Integer.parseInt(userId));

        Map<String,Integer> billMap=new HashMap<>();

        List<FoodItems> foodItemsList=user.get().getFoodItems();

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
        model.addAttribute("billMap",billMap);
        model.addAttribute("finalMap",finalMap);
        model.addAttribute("totalBillPrice",totalBillPrice);

        return "default/bill-page";


    }

}
