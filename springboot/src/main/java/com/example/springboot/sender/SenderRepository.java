package com.example.springboot.sender;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderRepository extends JpaRepository<Sender, Long> {
    //  SELECT * s from Sender s where s.id = id
    Optional<Sender> findById(long id);
    Optional<Sender> findByEmail(String email);
    
}
