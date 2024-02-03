package com.demo.buddy.service;

import com.demo.buddy.entity.Compte;
import com.demo.buddy.entity.Role;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * methods related to compte that are implemented from compte interface.
 * @author Mougni
 *
 */
@Service
public class CompteService implements ICompteService {

    @Autowired
    CompteRepository compteRepository;

    /**
     * this method update the info of a compte.
     * @param coordonnesBancaire represents a field udpated from the view.
     * @param userId represents the user id that we want to update his compte.
     * @return the compte updated.
     */
    public Compte updateCompte(String coordonnesBancaire, int userId){
        Compte compte = compteRepository.findCompteByUser(userId);

        if(compte.getCoordonneesBancaire() != coordonnesBancaire){
            compte.setCoordonneesBancaire(coordonnesBancaire);
        }

        return compteRepository.save(compte);
    }
}
