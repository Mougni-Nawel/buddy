package com.demo.buddy.service;

import com.demo.buddy.controller.exception.NotNecessaryFundsException;
import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.Operation;
import com.demo.buddy.entity.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface IOperationService {

    List<Operation> findTransactionsMadeByUser(int user);

    boolean newOperation(Operation operation, User user) throws NotNecessaryFundsException;

    double getCommission(Operation operation);

    double creditFriend(User user, double montant);



}
