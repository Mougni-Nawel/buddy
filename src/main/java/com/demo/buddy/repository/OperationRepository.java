package com.demo.buddy.repository;

import com.demo.buddy.entity.Amis;
import com.demo.buddy.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    @Query(value = "SELECT * FROM operation WHERE id_user =:userId", nativeQuery = true)
    List<Operation> findAllByUser(int userId);

    @Query("SELECT MAX(o.numeroTransaction) FROM Operation o")
    Integer getNextTransactionNumber();

}
