package com.demo.buddy.controller;

import com.demo.buddy.entity.Amis;
import com.demo.buddy.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.demo.buddy.service.IAmisService;
import com.demo.buddy.service.IOperationService;
import com.demo.buddy.service.IUserService;
import com.demo.buddy.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * test: get friend and save friend.
 * @author Mougni
 *
 */
@AutoConfigureMockMvc
@EnableWebMvc
//@SpringBootTest(properties = "spring.main.lazy-initialization=true",classes = AmisController.class)
@WebMvcTest
@WithMockUser
@Slf4j
public class AmisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @MockBean
    private IUserService userService;

    @MockBean
    private IOperationService operationService;


    @MockBean
    private IAmisService amisService;

    @BeforeEach
    public void setUpPerTest(){
        user = new User();
    }

    //add friend
    @Test
    @WithMockUser
    public void givenUser_whenAddFriend_then201IsReceived() throws Exception {
        setUpPerTest();
        user.setEmail("test1@test.com");
        user.setMdp("mdp10203");
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setRole("USER");

        Mockito.when(userService.findUserByEmail(user.getEmail())).thenReturn(user).toString();
        Mockito.when(amisService.addFriend(any(User.class), any(User.class))).thenReturn(true);

        User user1 = userService.findUserByEmail(user.getEmail());
        assertEquals("test1@test.com", user1.getEmail());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/addFriend").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

// get addfriend
    @Test
    @WithMockUser
    public void _WhenGetAddFriendForm_then302IsReceived() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/addFriend").with(csrf()))
                .andExpect(status().isOk());

    }

    // get getFriend

    @Test
    @WithMockUser
    public void _WhenGetFriendForm_then302IsReceived() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/getFriend").with(csrf()))
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
