package com.demo.buddy.service;

import com.demo.buddy.controller.exception.NotNecessaryFundsException;
import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.Compte;

public interface ICompteService {

    Compte updateCompte(String coordonnesBancaire, int userId);

    boolean debitAccount(Double amount) throws NotNecessaryFundsException, UserException;

}
