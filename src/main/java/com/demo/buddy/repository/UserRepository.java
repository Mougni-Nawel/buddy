package com.demo.buddy.repository;

import com.demo.buddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(final String email);

    User findById(final int id);

}
