package com.codemind.FoodHub.controller;

import com.codemind.FoodHub.entity.User;
import com.codemind.FoodHub.service.UserService;
import com.codemind.FoodHub.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private Logger logger=Logger.getLogger(getClass().getName());

    @Autowired
    UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){

        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);

    }

    @GetMapping("/registerPage")
    public String showRegisterPage(Model model){
        model.addAttribute("webUser",new WebUser());
        System.out.println("Hello");
        return "default/register/register";
    }

    @PostMapping("/processRegForm")
    public String processRegistrationForm(@Valid @ModelAttribute("webUser") WebUser webUser, BindingResult bindingResult, HttpSession httpSession, Model model){

        String userName= webUser.getUserName();
        logger.info("Processing registration for : "+userName);

        //check validation
        if(bindingResult.hasErrors()){
            return "default/register/register";
        }

        // check user already exists or not
        User existingUser=userService.findByUserName(userName);

        if(existingUser != null){
                model.addAttribute("webUser",new WebUser());
                model.addAttribute("registrationError","Username already exists!");

                logger.warning("Username already exists.");
                return "default/register/register";
        }

        userService.save(webUser);

        logger.info("Successfully created user : "+userName);

        httpSession.setAttribute("user",webUser);

        return "redirect:/loginPage";

    }


}
