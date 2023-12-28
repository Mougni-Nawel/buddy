package com.demo.buddy.repository;

import com.demo.buddy.entity.Amis;
import com.demo.buddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AmisRepository extends JpaRepository<Amis, Integer> {


    //@Query(value = "SELECT a FROM amis a WHERE a.user.id=:userid")
    //@Query(value = "SELECT * FROM amis WHERE id_user=:userid")
    @Query("SELECT a FROM Amis a WHERE a.idUser = :userid")
    List<Amis> findAmiByIdUser(int userid);

}
