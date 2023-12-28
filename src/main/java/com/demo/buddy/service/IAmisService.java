package com.demo.buddy.service;

import com.demo.buddy.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IAmisService {

    List<Optional<User>> findAmi();

    boolean addFriend(User userFind, User userId);

    List<Optional<User>> findAmisByIdUser(int userId);

}
