package com.demo.buddy.service;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IAmisService {

    List<Optional<User>> findAmi();

    Boolean checkIsAmis(int amiId, int userId) throws UserException;

    boolean addFriend(User userFind, User userId) throws UserException;

    List<Optional<User>> findAmisByIdUser(int userId);

}
