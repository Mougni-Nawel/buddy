package com.demo.buddy.service;

import com.demo.buddy.controller.exception.NotNecessaryFundsException;
import com.demo.buddy.controller.exception.UserException;
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

    @Autowired
    private UserService userService;


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

    public boolean debitAccount(Double amount) throws NotNecessaryFundsException, UserException {

        User user = userService.findUser();

        Compte compte = user.getCompteBancaire();

        if(amount <= compte.getMontant()){
            if(compte.getCoordonneesBancaire() != null){
                double newAmount = compte.getMontant() - amount;
                compte.setMontant(newAmount);

                compteRepository.save(compte);
            }else{
                throw new UserException("Veuillez renseignez votre iban");
            }

        }else{
            throw new NotNecessaryFundsException("Pas assez d'argent");
        }

        return true;
    }

}
