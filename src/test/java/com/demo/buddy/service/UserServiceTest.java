package com.demo.buddy.service;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.Compte;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

        //when(passwordEncoder.encode(anyString())).thenReturn(anyString());

        // then

        //userService.saveUser(newUser);
        assertThrows(UserException.class, () -> userService.saveUser(newUser));

        verify(passwordEncoder, times(0)).encode(anyString());
        verify(userRepository, times(0)).save(newUser);
        verify(userRepository, times(1)).findByEmail(newUser.getEmail());



    }


    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testUpdateUser() {

        // Mocking SecurityContext and Authentication
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext((org.springframework.security.core.context.SecurityContext) securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(((org.springframework.security.core.context.SecurityContext) securityContext).getAuthentication()).thenReturn(authentication);

        // Mocking user details
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
        //verify(userService, times(1)).findUser();
        assertEquals(user, result);

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
        // Mock SecurityContext and Authentication
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Mock user details
        when(authentication.getPrincipal()).thenReturn(user);

        // when

        when(userRepository.findById(anyInt())).thenReturn(user);

        // service test
        User result = userService.findUser();

        // then
        verify(userRepository, times(1)).findById(1);

        assertEquals(user, result);
    }

    // todo add findUser of user not found

   // @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testFindById() {

        // when
        when(userRepository.findById(user.getUserid())).thenReturn(Optional.of(user));

        // then
        Optional<User> result = userService.findById(user.getUserid());

        verify(userRepository, times(1)).findById(user.getUserid());

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

}
