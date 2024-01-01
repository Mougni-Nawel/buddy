package com.demo.buddy.service;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

public interface IContactService {

    List<Optional<User>> getContact(int userId);

    Boolean checkIsContact(int userId);

    void addContact(User userFind, User userId) throws UserException;

}
