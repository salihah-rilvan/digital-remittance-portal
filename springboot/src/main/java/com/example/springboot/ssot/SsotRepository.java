package com.example.springboot.ssot;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

import javax.transaction.Transactional;


@Repository
public interface SsotRepository extends JpaRepository <Ssot, Long>{
    List<Ssot> findAll();
    
    @Transactional
    void deleteSsotBySsotId(long ssotId);
}