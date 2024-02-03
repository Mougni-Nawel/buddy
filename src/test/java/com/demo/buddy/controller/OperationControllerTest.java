package com.demo.buddy.controller;

import com.demo.buddy.controller.exception.NotNecessaryFundsException;
import com.demo.buddy.entity.Compte;
import com.demo.buddy.entity.Operation;
import com.demo.buddy.entity.Role;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.AmisRepository;
import com.demo.buddy.service.AmisService;
import com.demo.buddy.service.IOperationService;
import com.demo.buddy.service.IUserService;
import com.demo.buddy.service.OperationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * test: save a transaction
 * @author Mougni
 *
 */
@WithMockUser
@WebMvcTest(OperationController.class)
@Slf4j
public class OperationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOperationService operationService;

    @MockBean
    private IUserService userService;

    @MockBean
    private AmisRepository amisRepository;
    private Operation operation;

    private User debiteur;

    @BeforeEach
    public void setUpPerTest(){
        operation = new Operation();
    }

    @Test
    @WithMockUser(username = "spring", password = "secret")
    public void givenOperation_whenCreateOperation_then202IsReceived() throws Exception {
        setUpPerTest();
        User user = new User();
        user.setEmail("test1@test.com");
        user.setMdp("mdp10203");
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setRole(Role.USER_GITHUB);

        operation.setAmi(user);
        operation.setMontant(129.00);

        Date date = new Date();
        operation.setDate(date);

        when(userService.findUser()).thenReturn(user);
        when(operationService.newOperation(any(Operation.class), any(User.class))).thenReturn(true);

        mockMvc.perform(post("/newTransaction").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(operation))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());


    }



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
