package com.demo.buddy.controller;

import com.demo.buddy.controller.exception.NotNecessaryFundsException;
import com.demo.buddy.entity.Operation;
import com.demo.buddy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OperationController {

    @Autowired
    IUserService userService;

    @Autowired
    IOperationService operationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/newTransaction")
    public String newTransaction(@ModelAttribute Operation operation, RedirectAttributes redirectAttributes) throws NotNecessaryFundsException {

        //System.out.println("DESTINATAIRE DE L'OPERATION : " + operation.getAmi().getUserid());
        operationService.newOperation(operation, userService.findUser(), redirectAttributes);

        //return "redirect:/home";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/home");
        //return new ResponseEntity<>("Operation created successfully", headers, HttpStatus.CREATED);
        return "home";
    }


}
