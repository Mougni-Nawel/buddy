package com.demo.buddy.repository;

import com.demo.buddy.entity.Amis;
import com.demo.buddy.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query(value = "SELECT * FROM contact WHERE id_user =:userid", nativeQuery = true)
    List<Contact> findContactByIdUser(int userid);

    @Query(value = "SELECT * FROM contact WHERE amiid =:amiid", nativeQuery = true)
    Contact findContactByIdPerson(int amiid);

}
