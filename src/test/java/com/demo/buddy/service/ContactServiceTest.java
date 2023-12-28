package com.demo.buddy.service;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = IContactService.class)
@AutoConfigureMockMvc
public class ContactServiceTest {


//    @Mock
//    private ContactRepository contactRepository;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private ContactServiceTest contactService;
//
//    private User user = new User();
//    private User userContact = new User();
//    private Contact contact = new Contact();
//
//
//    @BeforeEach
//    void setUp(){
//
//        userContact.setEmail("user1@mail.com");
//        userContact.setUserid(1);
//        userContact.setFirstname("User1");
//        userContact.setCompteBancaire(new Compte(1, "AZERTYVBNs", 20.01, userContact));
//
//        contact.setUser(user);
//        contact.setAmiID(userContact.getUserid());
//        contact.setId(1);
//
//        user.setEmail("user2@mail.com");
//        user.setUserid(2);
//        user.setFirstname("Thomas");
//        user.setCompteBancaire(new Compte(2, "ZERTYUIOG", 123.97, user));
//        user.setContact((Collection<Contact>) contact);
//
//    }
//
//    @Test
//    @WithMockUser(username = "user1", authorities = {"USER"})
//    void testGetContact() {
//
//        List<Contact> contacts = new ArrayList<>();
//        contacts.add(contact);
//
//        List<Optional<User>> contactInfos = new ArrayList<>();
//
//        contactInfos.add(Optional.ofNullable(userContact));
//
//        // when
//
//        when(contactRepository.findContactByIdUser(user.getUserid())).thenReturn(contacts);
//        when(userService.findById(contacts.get(0).getAmiID())).thenReturn(Optional.ofNullable(userContact));
//
//        // then
//
//        verify(contactRepository, times(1)).findContactByIdUser(user.getUserid());
//        verify(userService, times(1)).findById(contacts.get(0).getAmiID());
//
//        List<Optional<User>> result = contactService.getContact(user.getUserid());
//
//
//
//        Assertions.assertEquals(contactInfos, result);
//
//    }
//
//    @Test
//    @WithMockUser(username = "user1", authorities = {"USER"})
//    void testCheckIsContact() {
//
//        // when
//
//        when(contactRepository.findContactByIdPerson(userContact.getUserid())).thenReturn(contact);
//
//        // then
//
//        Assertions.assertTrue(true, contactService.checkIsContact(user.getUserid()));
//    }
//
//    @Test
//    @WithMockUser(username = "user1", authorities = {"USER"})
//    void testAddContact(){
//
//        Contact newContact = new Contact();
//        newContact.setUser(userContact);
//        newContact.setAmiID(user.getUserid());
//
//        // when
//
//        when(contactRepository.save(contact)).thenReturn(contact);
//        when(contactRepository.save(contact)).thenReturn(newContact);
//
//
//
//        // then
//
//        verify(contactRepository.save(any(Contact.class)), times(2));
//
//    }

}
