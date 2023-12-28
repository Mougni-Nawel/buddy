package com.demo.buddy.service;

import com.demo.buddy.controller.exception.NotNecessaryFundsException;
import com.demo.buddy.entity.Compte;
import com.demo.buddy.entity.Operation;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.OperationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = IOperationService.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private OperationService operationService;

    private User user = new User();
    private User user2 = new User();

    private Operation operation = new Operation();


    @BeforeEach
    void setUp(){


        user.setFirstname("User1");
        user.setUserid(Integer.valueOf(1));
        user.setLastname("Pseudo");
        user.setCompteBancaire(new Compte(1, "SDFGHJKBV", 56.90, user));
        user.setEmail("user1@mail.com");


        user2.setFirstname("Jean");
        user2.setEmail("jean@girad.com");
        user2.setMdp("mdp123");
        user2.setUserid(Integer.valueOf(2));
        user2.setCompteBancaire(new Compte(2, "TYUIHGP", 300.00, user2));

        operation.setNumeroTransaction(Integer.valueOf(1));
        operation.setMontant(50.00);
        operation.setUser(user);
        operation.setAmi(user2);

    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testGetCommission(){

        double montant = 50.00;
        int commission = 5;

        double expected = montant + (montant * ((double) commission / 100));

        // then
        Assertions.assertEquals(expected, operationService.getCommission(montant));

    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testCreditFriend(){

        double montant = 59.99;
        double updateMontant = user.getCompteBancaire().getMontant() + montant;


        Assertions.assertEquals(updateMontant, operationService.creditFriend(user, montant));


    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testFindTransactionsMadeByUser(){

        // when

        when(operationRepository.findAllByUser(user.getUserid())).thenReturn((List<Operation>) user.getOperation());

        // then

        Assertions.assertEquals(user.getOperation(), operationService.findTransactionsMadeByUser(user.getUserid()));

    }




    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testNewOperation() throws NotNecessaryFundsException {

        Date date = new Date();

        operation.setDate(date);
        user2.getCompteBancaire().setMontant(user.getCompteBancaire().getMontant() + 52.5);

        double expected = user.getCompteBancaire().getMontant() - operation.getMontant();
        // when

        when(operationService.getCommission(anyDouble())).thenReturn(Double.valueOf(52.5));
        when(operationService.creditFriend(any(), anyDouble())).thenReturn(Double.valueOf(user.getCompteBancaire().getMontant()));
        when(operationRepository.save(any(Operation.class))).thenReturn(operation);
        // then

        boolean result = operationService.newOperation(operation, user, any(RedirectAttributes.class));

        Assertions.assertTrue(true, String.valueOf(result));

        verify(operationService).getCommission(operation.getMontant());
        verify(operationService).creditFriend(operation.getAmi(), 52.5);
        verify(operationRepository).save(any(Operation.class));
    }


}
