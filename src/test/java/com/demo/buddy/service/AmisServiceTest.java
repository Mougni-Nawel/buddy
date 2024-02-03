package com.demo.buddy.service;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.Compte;
import com.demo.buddy.entity.User;
import com.demo.buddy.entity.Amis;
import com.demo.buddy.repository.AmisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * test: find a friend, add a friend, find all friends by an id user.
 * @author Mougni
 *
 */
@SpringBootTest(classes = IAmisService.class)
@AutoConfigureMockMvc
public class AmisServiceTest {

    @Mock
    private AmisRepository amisRepository;

    @Mock
    private UserService userService;
    @InjectMocks
    AmisService amisService;

    private User userFind = new User();
    private User userId = new User();


    @BeforeEach
    void setUp(){
        userFind.setEmail("user1@mail.com");
        userFind.setUserid(1);
        userFind.setFirstname("User1");
        userFind.setCompteBancaire(new Compte(1, "AZERTYVBNs", 20.01, userFind));

        userId.setEmail("user2@mail.com");
        userId.setUserid(2);
        userId.setFirstname("Thomas");
        userId.setCompteBancaire(new Compte(2, "ZERTYUIOG", 123.97, userId));

    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testFindAmi() {
        // Mocking SecurityContext and Authentication
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext((org.springframework.security.core.context.SecurityContext) securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(((org.springframework.security.core.context.SecurityContext) securityContext).getAuthentication()).thenReturn(authentication);

        // Mocking user details
        User principalUser = new User();
        principalUser.setUserid(1);
        when(authentication.getPrincipal()).thenReturn(principalUser);

        // Mocking Amis data
        Amis ami1 = new Amis();
        ami1.setIdAmis(2);


        Amis ami2 = new Amis();
        ami2.setIdAmis(3);

        when(userService.getUserId()).thenReturn(1);

        when(amisRepository.findAmiByIdUser(1)).thenReturn(Arrays.asList(ami1, ami2));

        User user2 = new User();
        user2.setUserid(2);
        when(userService.findById(2)).thenReturn(Optional.of(user2));

        User user3 = new User();
        user3.setUserid(3);
        when(userService.findById(3)).thenReturn(Optional.of(user3));

        List<Optional<User>> result = amisService.findAmi();

        verify(amisRepository, times(1)).findAmiByIdUser(1);
        verify(userService, times(1)).findById(2);
        verify(userService, times(1)).findById(3);

        // Asserting the result
        assertEquals(2, result.size());
        assertEquals(Optional.of(user2), result.get(0));
        assertEquals(Optional.of(user3), result.get(1));
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testAddFriend() throws UserException {

        // authentication
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext((org.springframework.security.core.context.SecurityContext) securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(((org.springframework.security.core.context.SecurityContext) securityContext).getAuthentication()).thenReturn(authentication);

        User principalUser = new User();
        principalUser.setUserid(1);
        when(authentication.getPrincipal()).thenReturn(principalUser);

        User userFind = new User();
        userFind.setEmail("user1@mail.com");
        userFind.setUserid(1);
        userFind.setFirstname("User1");
        userFind.setCompteBancaire(new Compte(1, "AZERTYVBNs", 20.01, userFind));

        User userId = new User();
        userId.setEmail("user2@mail.com");
        userId.setUserid(2);
        userId.setFirstname("Thomas");
        userId.setCompteBancaire(new Compte(2, "ZERTYUIOG", 123.97, userId));

        boolean result = amisService.addFriend(userFind, userId);

        verify(amisRepository, Mockito.times(2)).save(any());

        assertTrue(result);



    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testCheckIsAmis() throws UserException {

        // authentication
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when((securityContext).getAuthentication()).thenReturn(authentication);

        when(amisRepository.findContactByIdPerson(anyInt(), anyInt())).thenReturn(null);

        boolean result = amisService.checkIsAmis(userFind.getUserid(), userId.getUserid());

        verify(amisRepository, Mockito.times(1)).findContactByIdPerson(anyInt(), anyInt());

        assertTrue(result);


    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testFindAmisByIdUser() {


        // authentication
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext((org.springframework.security.core.context.SecurityContext) securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(((org.springframework.security.core.context.SecurityContext) securityContext).getAuthentication()).thenReturn(authentication);

        User principalUser = new User();
        principalUser.setUserid(1);
        when(authentication.getPrincipal()).thenReturn(principalUser);

        List<Amis> amiList = new ArrayList<>();
        Amis ami = new Amis();
        ami.setIdUser(userId.getUserid());
        ami.setId(1);
        ami.setIdAmis(userFind.getUserid());

        amiList.add(ami);
        List<Optional<User>> amisInfos = new ArrayList<>();

        amisInfos.add(Optional.ofNullable(userFind));

        when(amisRepository.findAmiByIdUser(userId.getUserid())).thenReturn(amiList);
        when(userService.findById(ami.getIdAmis())).thenReturn(Optional.ofNullable(userFind));

        List<Optional<User>> result = amisService.findAmisByIdUser(userId.getUserid());

        verify(amisRepository, Mockito.times(1)).findAmiByIdUser(userId.getUserid());
        verify(userService, Mockito.times(1)).findById(ami.getIdAmis());

        assertEquals(amisInfos, result);

    }

    @Test
    @WithMockUser(username = "user1", authorities = {"USER"})
    void testFindAmisByIdUserEmpty() {

        // authentication
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext((org.springframework.security.core.context.SecurityContext) securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(((org.springframework.security.core.context.SecurityContext) securityContext).getAuthentication()).thenReturn(authentication);

        User principalUser = new User();
        principalUser.setUserid(1);
        when(authentication.getPrincipal()).thenReturn(principalUser);

        List<Amis> amiList = new ArrayList<>();

        List<Optional<User>> amisInfos = new ArrayList<>();

        when(amisRepository.findAmiByIdUser(userId.getUserid())).thenReturn(amiList);

        List<Optional<User>> result = amisService.findAmisByIdUser(userId.getUserid());

        verify(amisRepository, Mockito.times(1)).findAmiByIdUser(userId.getUserid());
        verify(userService, Mockito.times(0)).findById(anyInt());

        assertEquals(amisInfos, result);

    }



}
