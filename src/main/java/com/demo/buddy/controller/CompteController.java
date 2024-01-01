package com.demo.buddy.controller;

import com.demo.buddy.entity.User;
import com.demo.buddy.service.IUserService;
import com.demo.buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CompteController {

    @Autowired
    IUserService userService;

    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@ModelAttribute("user") User user, String coordonneesBancaire, Model model){

        System.out.println("LE USER UPDATE : "+user);
        System.out.println("LE COMPTE UPDATE : "+coordonneesBancaire);
        //compteService.updateCompte(coordonneesBancaire, userService.findIdUserLogged());
        model.addAttribute("updateUserInfo", user);
        userService.updateUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/home");
        return "/home";


    }

    @GetMapping("/profile")
    public String getProfile(Model model) {

        User userConnected = userService.findUser();

        model.addAttribute("user", userConnected);
        System.out.println(userConnected.getCompteBancaire().getCoordonneesBancaire());

        return "login";


    }

    @GetMapping("/updateAccount")
    public String getAccount(Model model) {

        model.addAttribute("user", new User());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/updateAccount");
        return "updateAccount";


    }


    @PostMapping("/updateAccount")
    public String updateUserInfo(@ModelAttribute("user") User user, Model model){

        //compteService.updateCompte(coordonneesBancaire, userService.findIdUserLogged());
        //model.addAttribute("updateUserInfo", user);
        userService.updateAccount(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/home");
        return "home";


    }

}
