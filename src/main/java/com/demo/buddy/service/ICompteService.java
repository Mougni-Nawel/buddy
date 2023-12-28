package com.demo.buddy.service;

import com.demo.buddy.entity.Compte;

public interface ICompteService {

    Compte updateCompte(String coordonnesBancaire, int userId);

}
