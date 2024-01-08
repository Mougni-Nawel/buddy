package com.demo.buddy.controller;

import com.demo.buddy.entity.User;
import com.demo.buddy.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * test: register a user, show view
 * @author Mougni
 *
 */
@AutoConfigureMockMvc
@EnableWebMvc
//@SpringBootTest(classes = UserController.class)
@WebMvcTest
@WithMockUser
@Slf4j
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @MockBean
    private IUserService userService;

    @MockBean
    private IAmisService amisService;

    @MockBean
    private IOperationService operationService;

    @BeforeEach
    public void setUpPerTest(){
        user = new User();
    }

    // okay signup
    @Test
    @WithMockUser(username = "spring", password = "secret")
    public void givenUser_whenUserSignup_then202IsReceived() throws Exception {
        setUpPerTest();
        user.setEmail("test1@test.com");
        user.setMdp("mdp10203");
        user.setFirstname("Test");
        user.setLastname("Test");
        user.setRole("USER");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/signup").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());

        //userService.saveUser(user);

        //Mockito.verify(passwordEncoder, Mockito.times(1)).encode(user.getPassword());
        //Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    // get a signup page
    @Test
    @WithMockUser
    public void _WhenGetFriendForm_then302IsReceived() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/signup").with(csrf()))
                .andExpect(status().isFound());

    }


    // bad signup
//    @Test
//    @WithMockUser(username = "spring", password = "secret")
//    public void givenUserThatExist_whenUserSignup_then404IsReceived() throws Exception {
//        setUpPerTest();
//
//        User userExist = new User();
//        userExist.setEmail("test1@test.com");
//        userExist.setMdp("mdp10203");
//        userExist.setFirstname("Test");
//        userExist.setLastname("Test");
//        userExist.setRole("USER");
//
//
//
//        user.setEmail("test1@test.com");
//        user.setMdp("mdp10203");
//        user.setFirstname("Test");
//        user.setLastname("Test");
//        user.setRole("USER");
//
//        //doThrow(ChangeSetPersister.NotFoundException.class).when(userService).saveUser(any(User.class));
//        //doThrow(new UserAlreadyExistException("This user already exists")).when(userService).saveUser(any(User.class));
//
//        UserService userService = Mockito.mock(UserService.class);
//        Mockito.doThrow(new UserAlreadyExistException("This user already exists"))
//                .when(userService)
//                .saveUser(user);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/signup").with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(user))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotAcceptable());
//    }


    // redirection pour la page signups


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
