package com.demo.buddy.controller;
import com.demo.buddy.entity.Compte;
import com.demo.buddy.entity.User;
import com.demo.buddy.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * test: update user account, update user info, show views
 * @author Mougni
 *
 */
@WebMvcTest(CompteController.class)
public class CompteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void given_whenGetViewProfile_then200IsReceived() throws Exception {
        User user = new User();
        user.setFirstname("User1");
        user.setUserid(Integer.valueOf(1));
        user.setLastname("Pseudo");
        user.setCompteBancaire(new Compte(1, "SDFGHJKBV", 56.90, user));
        user.setEmail("user1@mail.com");

        Mockito.when(userService.findUser()).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void given_whenGetViewUpdateAccount_then200IsReceived() throws Exception {
        User user = new User();
        user.setFirstname("User1");
        user.setUserid(Integer.valueOf(1));
        user.setLastname("Pseudo");
        user.setCompteBancaire(new Compte(1, "SDFGHJKBV", 56.90, user));
        user.setEmail("user1@mail.com");


        mockMvc.perform(MockMvcRequestBuilders.get("/updateAccount"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void given_whenGetAUserUpdateUser_then200IsReceived() throws Exception {
        User user = new User();
        user.setFirstname("User1");
        user.setUserid(Integer.valueOf(1));
        user.setLastname("Pseudo");
        user.setCompteBancaire(new Compte(1, "SDFGHJKBV", 56.90, user));
        user.setEmail("user1@mail.com");

        Mockito.when(userService.updateUser(ArgumentMatchers.any(User.class))).thenReturn(user);

        User result = userService.updateUser(user);

        Assertions.assertEquals("user1@mail.com", result.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.post("/updateUserInfo").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void given_whenGetAUserUpdateAccount_then200IsReceived() throws Exception {
        User user = new User();
        user.setFirstname("User1");
        user.setUserid(Integer.valueOf(1));
        user.setLastname("Pseudo");
        user.setCompteBancaire(new Compte(1, "SDFGHJKBV", 56.90, user));
        user.setEmail("user1@mail.com");

        Mockito.doNothing().when(userService).updateAccount(ArgumentMatchers.any(User.class));

        userService.updateUser(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/updateAccount").with(csrf()))
                .andExpect(status().isOk());
    }

}
