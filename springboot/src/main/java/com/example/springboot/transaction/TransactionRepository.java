package com.example.springboot.transaction;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT * FROM transaction t WHERE t.sender_id = ?1", nativeQuery = true)
    ArrayList<Transaction> getTransactionBySender(long id);

    @Query(value = "SELECT * FROM transaction t WHERE t.status='rejected' OR t.status='error' LIMIT 500", nativeQuery = true)
    ArrayList<Transaction> getAllFailTransactions();

    @Query(value = "SELECT * FROM transaction t  LIMIT 500", nativeQuery = true)
    ArrayList<Transaction> getAllTransactions();

}

