package com.demo.buddy.service;

import com.demo.buddy.controller.exception.NotNecessaryFundsException;
import com.demo.buddy.entity.Operation;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.OperationRepository;
import com.demo.buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OperationService implements IOperationService{

    @Autowired
    OperationRepository operationRepository;

    static final double commission = 5;

    public List<Operation> findAll(){

        return operationRepository.findAll();

    }

    public List<Operation> findTransactionsMadeByUser(int user){
        return operationRepository.findAllByUser(user);
    }

    public boolean newOperation(Operation operation, User user, RedirectAttributes redirectAttributes) throws NotNecessaryFundsException {
        if(user.getCompteBancaire().getMontant() >= operation.getMontant()){
            Date localDate = new Date();

            Operation newOperation = new Operation();
            newOperation.setDate(localDate);
            newOperation.setMontant(operation.getMontant());
            newOperation.setAmi(operation.getAmi());
            newOperation.setUser(user);

            double montantAfterCommission = getCommission(newOperation.getMontant());

            creditFriend(operation.getAmi(), montantAfterCommission);
            double updateMontant = user.getCompteBancaire().getMontant() - operation.getMontant();

            user.getCompteBancaire().setMontant(updateMontant);
            // add les deux personnes qui ont fait la transactions
            operationRepository.save(newOperation);

            return true;
        }else{
            redirectAttributes.addFlashAttribute("errorMessage", "Pas assez d'argent");
            throw new NotNecessaryFundsException("Pas assez d'argent");
            //todo ajouter un message d'erreur et une exception
        }


    }

    public double getCommission(double montant) {
        System.out.println("COMMISSION : "+(montant * (commission / 100)));
        return montant + (montant * (commission / 100));
    }

    public double creditFriend(User user, double montant){
        double updateMontant = user.getCompteBancaire().getMontant() + montant;
        user.getCompteBancaire().setMontant(updateMontant);
        return user.getCompteBancaire().getMontant();
    }

}
