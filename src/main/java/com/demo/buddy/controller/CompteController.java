package com.demo.buddy.controller;

import com.demo.buddy.entity.User;
import com.demo.buddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * methods related to the controller of compte
 * @author Mougni
 *
 */
@Controller
public class CompteController {

    @Autowired
    IUserService userService;

    /**
     * this method update the info of the user from the path /updateUserInfo
     * @param user represent the user info that has to be modified.
     * @param model is used as a parameter to pass to the view all the user info.
     * @return the view home.
     */
    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@ModelAttribute("user") User user, Model model){

        model.addAttribute("updateUserInfo", user);
        userService.updateUser(user);

        return "redirect:/home";


    }

    /**
     * this method get the view login from the path /profile
     * @param model is used as a parameter to pass to the view all the user info.
     * @return the view login.
     */
    @GetMapping("/profile")
    public String getProfile(Model model) {

        User userConnected = userService.findUser();

        model.addAttribute("user", userConnected);

        return "accountInfo";


    }

    /**
     * this method get the view updateAccount from the path /updateAccount
     * @param model is used as a parameter to pass to the view a user to update info.
     * @return the view updateAccount.
     */
    @GetMapping("/updateAccount")
    public String getAccount(Model model) {

        model.addAttribute("user", new User());

        return "updateAccount";


    }


    /**
     * this method update the info of the account of a user from the path /updateAccount
     * @param user represent the user account info that has to be modified.
     * @return the view home.
     */
    @PostMapping("/updateAccount")
    public String updateAccount(@ModelAttribute("user") User user){

        userService.updateAccount(user);

        return "redirect:/home";


    }

}
