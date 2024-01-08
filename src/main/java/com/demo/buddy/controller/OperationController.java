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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * methods related to the controller of operation
 * @author Mougni
 *
 */
@Controller
public class OperationController {

    @Autowired
    IUserService userService;

    @Autowired
    IOperationService operationService;

    /**
     * this method create a new transaction from the path /newTransaction
     * @param operation represent the operation that has to be created.
     * @return the view home.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/newTransaction")
    public String newTransaction(@ModelAttribute Operation operation) throws NotNecessaryFundsException {

        operationService.newOperation(operation, userService.findUser());

        return "redirect:/home";
    }


}
