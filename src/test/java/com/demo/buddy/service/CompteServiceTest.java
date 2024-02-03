package com.demo.buddy.service;

import com.demo.buddy.entity.Compte;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.CompteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.mockito.Mockito.*;

/**
 * test: update an account.
 * @author Mougni
 *
 */
@SpringBootTest(classes = ICompteService.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CompteServiceTest {


    @Mock
    private CompteRepository compteRepository;

    @InjectMocks
    private CompteService compteService;

    private Compte compte1;

    private User user;


    @BeforeEach
    void setUp(){

        user = new User();

        user.setFirstname("User1");
        user.setUserid(1);
        user.setLastname("Pseudo");
        user.setEmail("user1@mail.com");

        compte1 = new Compte(1, "SDFGHJKBV", 56.90, user);


    }


    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testUpdateCompte() {

        // when

        when(compteRepository.findCompteByUser(user.getUserid())).thenReturn(compte1);

        when(compteRepository.save(compte1)).thenReturn(compte1);

        // then

        user.setEmail("user@mail.com");

        Compte result = compteService.updateCompte(String.valueOf(user.getCompteBancaire()), user.getUserid());
        verify(compteRepository, times(1)).save(compte1);
        assertEquals(compte1, result);


    }
}
