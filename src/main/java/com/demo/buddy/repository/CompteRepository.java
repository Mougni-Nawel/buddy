package com.demo.buddy.repository;

import com.demo.buddy.entity.Amis;
import com.demo.buddy.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {

    @Query(value = "SELECT * FROM compte WHERE user=:userid", nativeQuery = true)
    Compte findCompteByUser(int userid);

}
