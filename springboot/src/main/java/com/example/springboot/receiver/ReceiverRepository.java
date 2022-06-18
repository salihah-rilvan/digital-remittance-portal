package com.example.springboot.receiver;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepository extends JpaRepository <Receiver,Long>{
    Optional<Receiver> findById(long receiverId);
    
}
