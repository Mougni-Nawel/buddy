package com.demo.buddy.service;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.Compte;
import com.demo.buddy.entity.Role;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * test: save a new user, save a user that already exist, update a user, find a user.
 * @author Mougni
 *
 */
@SpringBootTest(classes = IUserService.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user = new User();




    @BeforeEach
    void setUp(){


        user.setFirstname("User1");
        user.setUserid(1);
        user.setLastname("Pseudo");
        user.setCompteBancaire(new Compte(1, "SDFGHJKBV", 56.90, user));
        user.setEmail("user1@mail.com");



    }


    @Test
    void testSaveUser() throws UserException {

        User newUser = new User();
        newUser.setEmail("newUser@mail.com");
        newUser.setMdp("password567");

        // when

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(null);

        when(passwordEncoder.encode(anyString())).thenReturn(anyString());

        // then

        userService.saveUser(newUser);

        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(newUser);


    }

    @Test
    void testSaveUserThatAlreadyExist() throws UserException {

        User newUser = new User();
        newUser.setEmail("newUser@mail.com");
        newUser.setMdp("password567");

        // when

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(newUser);

        // then

        assertThrows(UserException.class, () -> userService.saveUser(newUser));

        verify(passwordEncoder, times(0)).encode(anyString());
        verify(userRepository, times(0)).save(newUser);
        verify(userRepository, times(1)).findByEmail(newUser.getEmail());



    }


    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testUpdateUser() {

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext((org.springframework.security.core.context.SecurityContext) securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(((org.springframework.security.core.context.SecurityContext) securityContext).getAuthentication()).thenReturn(authentication);

        User principalUser = new User();
        principalUser.setUserid(1);
        when(authentication.getPrincipal()).thenReturn(principalUser);


        user.setEmail("user@mail.com");

        // when

        when(userService.findUser()).thenReturn(user);

        when(userRepository.save(user)).thenReturn(user);

        // then

        User result = userService.updateUser(user);
        verify(userRepository, times(1)).save(user);
        assertEquals(user, result);

    }


    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testUpdateAccount() {

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext((org.springframework.security.core.context.SecurityContext) securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(((org.springframework.security.core.context.SecurityContext) securityContext).getAuthentication()).thenReturn(authentication);

        User account = new User();
        account.setUserid(12);
        when(authentication.getPrincipal()).thenReturn(account);

        Compte compte1 = new Compte(1, "SDFGHJKBV", 56.90, account);
        account.setCompteBancaire(compte1);

        User userFound = new User();

        // when

        when(userService.findUser()).thenReturn(userFound);

        when(userRepository.save(userFound)).thenReturn(userFound);

        userFound.setEmail("usertest@test.com");
        userFound.setUserid(12);
        userFound.setCompteBancaire(compte1);
        userFound.getCompteBancaire().setMontant(10);

        // then
        userService.updateAccount(account);
        verify(userRepository, times(1)).save(userFound);
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testLoadUserByUsername(){

        // when

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // then

        User result = userService.loadUserByUsername(user.getEmail());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        assertEquals(user, result);

    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testLoadUserByUsernameNotFound(){

        // when

        when(userRepository.findByEmail("test@test.com")).thenReturn(null);

        // then

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("test@test.com"));


        verify(userRepository, times(1)).findByEmail("test@test.com");


    }


    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testFindUser() {

        // authentication
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(user);

        // when
        when(userRepository.findById(anyInt())).thenReturn(user);

        User result = userService.findUser();

        // then
        verify(userRepository, times(1)).findById(1);

        assertEquals(user, result);
    }


   @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testFindById() {

        // when
        when(userRepository.findById(anyInt())).thenReturn(user);

        // then
        Optional<User> result = userService.findById(user.getUserid());

        verify(userRepository, times(1)).findById(anyInt());

        assertEquals(Optional.of(user), result);
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testFindUserByEmail() {

        // when
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // then
        User result = userService.findUserByEmail(user.getEmail());

        verify(userRepository, times(1)).findByEmail(user.getEmail());

        assertEquals(user, result);

    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testGetUserId() {

        // authentication
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        user.setRole(Role.USER);
        when(authentication.getPrincipal()).thenReturn(user);


        int userId = user.getUserid();

        int result = userService.getUserId();

        verify(authentication, times(4)).getPrincipal();

        assertEquals(userId, result);

    }





}
