package com.demo.buddy.controller;

import com.demo.buddy.entity.User;
import com.demo.buddy.repository.UserRepository;
import com.demo.buddy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AmisController {

    @Autowired
    IAmisService amisService;

    @Autowired
    IUserService userService;


    @GetMapping("/addFriend")
    public String getAddFriend(Model model){
        model.addAttribute("user", new User());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/addFriend");
        return "addFriend";


    }



    @PostMapping("/addFriend")
    public String addFriend(@ModelAttribute User user, Model model, RedirectAttributes redirect){


        User userFind = userService.findUserByEmail(user.getEmail());
        //Optional<Amis> findAmi =  amisService.findAmiById(userFind.getUserid());
        amisService.addFriend(userFind, userService.findUser());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/home");
        return "home";

    }

    @GetMapping("/getFriend")
    public String getFriends(Model model){

        List<Optional<User>> amiInfos = amisService.findAmisByIdUser(userService.findIdUserLogged());

        model.addAttribute("ami", amiInfos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/getFriend");
        return "getFriend";

    }

}
