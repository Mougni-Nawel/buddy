package com.demo.buddy.controller;


import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.User;
import com.demo.buddy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    IUserService userService;


    //@ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/login")
    public String login(){
        return "welcome";
    }



    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("user", new User());

        return "signup";
    }

    @PostMapping("/signup")
    public String newUser(@ModelAttribute("user") User user, Model model) throws UserException {

        model.addAttribute("newUser", user);
        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/login");
        return "welcome";

    }

}
