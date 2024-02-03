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

/**
 * methods related to the controller of user
 * @author Mougni
 *
 */
@Controller
public class UserController {

    @Autowired
    IUserService userService;

    /**
     * this method shows the view to login from the path /login
     * @return the view welcome.
     */
    @GetMapping("/login")
    public String login(){
        return "welcome";
    }



    /**
     * this method shows the view to signup from the path /updateUserInfo
     * @param model is used as a parameter to pass to the view to signup.
     * @return the view signup.
     */
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("user", new User());

        return "signup";
    }

    /**
     * this method create an account for user that has been signup from the path /signup
     * @param user represent the user that has to be registered.
     * @param model is used as a parameter to pass to the use to the view.
     * @return the view welcome.
     */
    @PostMapping("/signup")
    public String newUser(@ModelAttribute("user") User user, Model model) throws UserException {

        model.addAttribute("newUser", user);
        userService.saveUser(user);

        return "welcome";

    }

    @GetMapping("/")
    public String getGitHub() {
        return "redirect:/home";
    }

}
