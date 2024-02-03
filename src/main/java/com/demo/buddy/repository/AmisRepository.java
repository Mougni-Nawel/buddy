package com.demo.buddy.repository;

import com.demo.buddy.entity.Amis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmisRepository extends JpaRepository<Amis, Integer> {

    @Query("SELECT a FROM Amis a WHERE a.idUser = :userid")
    List<Amis> findAmiByIdUser(int userid);

    @Query("SELECT a FROM Amis a WHERE a.IdAmis = :amiid AND a.idUser = :userid")
    Amis findContactByIdPerson(int amiid, int userid);

}
