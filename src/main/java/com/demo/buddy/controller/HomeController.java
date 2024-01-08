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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * methods related to the controller of home
 * @author Mougni
 *
 */
@Controller
public class HomeController {

    @Autowired
    IAmisService amisService;

    @Autowired
    IUserService userService;

    @Autowired
    IOperationService operationService;


    /**
     * this method get the view home from the path /home
     * @param model is used as a parameter to pass to the view all the friends and the transactions that had been made by the person.
     * @return the view home.
     */
    @GetMapping("/home")
    public String home(Model model) {

        List<Optional<User>> amisInfos = amisService.findAmi();

        model.addAttribute("amisInfo", amisInfos);

        List<Operation> operationsList = operationService.findTransactionsMadeByUser(userService.findIdUserLogged());
        model.addAttribute("transactions", operationsList);

        return "home";

    }


}
