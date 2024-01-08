package com.demo.buddy.controller;

import com.demo.buddy.entity.Operation;
import com.demo.buddy.entity.User;
import com.demo.buddy.service.IAmisService;
import com.demo.buddy.service.IOperationService;
import com.demo.buddy.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * test: get view home
 * @author Mougni
 *
 */
@AutoConfigureMockMvc
@EnableWebMvc
//@SpringBootTest(properties = "spring.main.lazy-initialization=true",classes = HomeController.class)
@WithMockUser
@WebMvcTest
@Slf4j
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAmisService amisService;
    @MockBean
    private IOperationService operationService;
    @MockBean
    private IUserService userService;

    @Test
    @WithMockUser
    public void _WhenGetHome_then302IsReceived() throws Exception {

        List<Optional<User>> list = new ArrayList<>();
        List<Operation> listOperations = new ArrayList<>();

        Mockito.when(amisService.findAmi()).thenReturn(list);
        Mockito.when(userService.findIdUserLogged()).thenReturn(1);
        Mockito.when(operationService.findTransactionsMadeByUser(ArgumentMatchers.anyInt())).thenReturn(listOperations);

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/home").with(csrf()))
                .andExpect(status().isOk());

    }

}
