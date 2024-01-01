package com.demo.buddy.controller;

import com.demo.buddy.entity.Compte;
import com.demo.buddy.entity.Contact;
import com.demo.buddy.entity.User;
import com.demo.buddy.service.IContactService;
import com.demo.buddy.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@EnableWebMvc
@SpringBootTest(properties = "spring.main.lazy-initialization=true",classes = ContactController.class)
@WithMockUser
@Slf4j
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private User user = new User();

    @MockBean
    private IContactService contactService;

    @MockBean
    private IUserService userService;

    private User userContact = new User();
    private Contact contact = new Contact();


    @BeforeEach
    void setUp(){

        userContact.setEmail("user1@mail.com");
        userContact.setUserid(1);
        userContact.setFirstname("User1");
        userContact.setCompteBancaire(new Compte(1, "AZERTYVBNs", 20.01, userContact));

        contact.setUser(user);
        contact.setAmiID(userContact.getUserid());
        contact.setId(1);

        user.setEmail("user2@mail.com");
        user.setUserid(2);
        user.setFirstname("Thomas");
        user.setCompteBancaire(new Compte(2, "ZERTYUIOG", 123.97, user));
        user.setContact(Collections.singletonList(contact));

    }

    @Test
    @WithMockUser
    public void given_whenGetContact_then201IsReceived() throws Exception {

        List<Optional<User>> contacts = new ArrayList<>();

        when(userService.findIdUserLogged()).thenReturn(1);
        when(contactService.getContact(anyInt())).thenReturn(contacts);

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/getContacts").with(csrf()))
                .andExpect(status().isOk());


        verify(contactService, times(1)).getContact(anyInt());
        verify(userService, times(1)).findIdUserLogged();
    }


    @Test
    @WithMockUser
    public void givenUser_whenAddPersonToContact_then201IsReceived() throws Exception {

        when(userService.findUserByEmail("user2@mail.com")).thenReturn(user);
        when(userService.findUser()).thenReturn(new User()); // Assuming you are mocking the logged-in user

        mockMvc.perform(post("/addPersonToContact").with(csrf())
                        .param("email", "user2@mail.com")) // Adjust with your form parameter
                .andExpect(status().isOk());

        verify(userService, times(1)).findUserByEmail("user2@mail.com");
        verify(userService, times(1)).findUser();
        verify(contactService, times(1)).addContact(any(User.class), any(User.class));

    }

    @Test
    @WithMockUser
    public void given_whenGetViewAddToContact_then201IsReceived() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.
                        get("/addPersonToContact").with(csrf()))
                .andExpect(status().isOk());


    }

}
