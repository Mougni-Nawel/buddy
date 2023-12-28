package com.demo.buddy.service;

import com.demo.buddy.entity.Compte;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompteService implements ICompteService {

    @Autowired
    CompteRepository compteRepository;

    public Compte updateCompte(String coordonnesBancaire, int userId){
        //User actualUserInfo = findUser();

        Compte compte = compteRepository.findCompteByUser(userId);


        if(compte.getCoordonneesBancaire() != coordonnesBancaire){
            compte.setCoordonneesBancaire(coordonnesBancaire);
        }

        return compteRepository.save(compte);
    }
}
