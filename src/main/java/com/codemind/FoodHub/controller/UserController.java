package com.codemind.FoodHub.controller;

import com.codemind.FoodHub.dao.UserDAO;
import com.codemind.FoodHub.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    AuthController authController;

    @Autowired
    UserDAO userDAO;

    @GetMapping("/update")
    String updateProfile(Model model){

        User user=userDAO.findByUserName(authController.getAuthenticateUserName());

        model.addAttribute("user",user);
        model.addAttribute("name",user.getFirstName()+" "+user.getLastName());

        return "default/user-update";

    }

    @PostMapping("/save")
    String saveUpdatedUser(@ModelAttribute("user") User user,Model model){

        User tempUser=userDAO.findByUserName(authController.getAuthenticateUserName());

        user.setPassword(user.getFirstName());

        userDAO.save(user);
        model.addAttribute("success",true);
        return "redirect:/";

    }

}
