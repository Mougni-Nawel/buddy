package com.demo.buddy.service;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {

    void saveUser(User newUser) throws UserException;

    User updateUser(User updateUserInfo);

    User loadUserByUsername(String username) throws UsernameNotFoundException;

    int findIdUserLogged();

    User findUserByEmail(String email);

    Optional<User> findById(int userid);

    User findUser();

    void updateAccount(User account);

    User findUserGit(String username);

    User saveUserGit(String username);

    int getUserId();

}
