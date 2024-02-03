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

/**
 * methods related to operation that are implemented from operation interface.
 * @author Mougni
 *
 */
@Service
@Transactional
public class OperationService implements IOperationService{

    @Autowired
    OperationRepository operationRepository;

    static final double commission = 5;


    public List<Operation> findAll(){

        return operationRepository.findAll();

    }

    /**
     * this method get all the transactions that had been made by the user that is put in parameter.
     * @param user represents the id of a user of who we want to get all the transactions.
     * @return a list of transactions.
     */
    public List<Operation> findTransactionsMadeByUser(int user){
        return operationRepository.findAllByUser(user);
    }

    /**
     * this method create a new transaction that is put in parameter.
     * @param operation represents the transaction that we want to save.
     * @param user represents the user that created the transaction.
     * @return boolean.
     * @throws NotNecessaryFundsException if the amount of account is not enough.
     */
    public boolean newOperation(Operation operation, User user) throws NotNecessaryFundsException {
        if(user.getCompteBancaire().getMontant() >= operation.getMontant()){
            Date localDate = new Date();

            Operation newOperation = new Operation();
            newOperation.setDate(localDate);
            newOperation.setMontant(operation.getMontant());
            newOperation.setAmi(operation.getAmi());
            newOperation.setUser(user);

            int nbTransaction;
            Integer nextTransactionNumber = operationRepository.getNextTransactionNumber();
            if(nextTransactionNumber == null || nextTransactionNumber == 0){
                nbTransaction = 0;
            }else{
                nbTransaction = nextTransactionNumber;
            }
            newOperation.setNumeroTransaction(nbTransaction + 1);

            double montantAfterCommission = getCommission(newOperation);

            creditFriend(operation.getAmi(), montantAfterCommission);
            double updateMontant = user.getCompteBancaire().getMontant() - operation.getMontant();

            user.getCompteBancaire().setMontant(updateMontant);
            operationRepository.save(newOperation);

            return true;
        }else{
            throw new NotNecessaryFundsException("Pas assez d'argent");
        }


    }

    /**
     * this method calculate the commision that will be taken from the amount of the transaction.
     * @param operation represents the new transaction.
     * @return a double of the amount re calculated.
     */
    public double getCommission(Operation operation) {
        System.out.println("COMMISSION : "+(operation.getMontant() * (commission / 100)));
        operation.setCommission(operation.getMontant() * (commission / 100));

        return operation.getMontant() - (operation.getMontant() * (commission / 100));
    }

    /**
     * this method add the amount of the transaction to the friend that has been put in parameter.
     * @param user represents the friend who will receive the money.
     * @param montant represents the amount of money that the friend will receive.
     * @return a double that represents the amount in the account of the friend.
     */
    public double creditFriend(User user, double montant){
        double updateMontant = user.getCompteBancaire().getMontant() + montant;
        user.getCompteBancaire().setMontant(updateMontant);
        return user.getCompteBancaire().getMontant();
    }

}
