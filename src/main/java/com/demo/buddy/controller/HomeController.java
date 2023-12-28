package com.demo.buddy.controller;

import com.demo.buddy.entity.Operation;
import com.demo.buddy.entity.User;
import com.demo.buddy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    IAmisService amisService;

    @Autowired
    IUserService userService;

    @Autowired
    IOperationService operationService;


    @GetMapping("/home")
    public String home(Model model) {

        List<Optional<User>> amisInfos = amisService.findAmi();

        model.addAttribute("amisInfo", amisInfos);

        // get all transactions

        List<Operation> operationsList = operationService.findTransactionsMadeByUser(userService.findIdUserLogged());
        model.addAttribute("transactions", operationsList);

        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Location", "/home");
        return "home";

    }


}
