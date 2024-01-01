package com.demo.buddy.service;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.Compte;
import com.demo.buddy.entity.Contact;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.ContactRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = IContactService.class)
@AutoConfigureMockMvc
public class ContactServiceTest {


    @Mock
    private ContactRepository contactRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ContactService contactService;

    private User user = new User();
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
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testGetContact() {

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);

        List<Optional<User>> contactInfos = new ArrayList<>();

        contactInfos.add(Optional.ofNullable(userContact));

        // when

        when(contactRepository.findContactByIdUser(anyInt())).thenReturn(contacts);
        when(userService.findById(anyInt())).thenReturn(Optional.ofNullable(userContact));

        // then



        List<Optional<User>> result = contactService.getContact(user.getUserid());

        verify(contactRepository, times(1)).findContactByIdUser(user.getUserid());
        verify(userService, times(1)).findById(contacts.get(0).getAmiID());

        Assertions.assertEquals(contactInfos, result);

    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testCheckIsContact() {

        // when

        when(contactRepository.findContactByIdPerson(anyInt())).thenReturn(contact);

        // then

        Assertions.assertTrue(contactService.checkIsContact(user.getUserid()));
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testAddContact() throws UserException {

        Contact newContact = new Contact();
        newContact.setUser(userContact);
        newContact.setAmiID(user.getUserid());

        // when

        when(contactRepository.save(any())).thenReturn(contact);
        //when(contactRepository.save(any())).thenReturn(newContact);

        contactService.addContact(user, userContact);

        // then

        verify(contactRepository, times(2)).save(any());

    }

}
