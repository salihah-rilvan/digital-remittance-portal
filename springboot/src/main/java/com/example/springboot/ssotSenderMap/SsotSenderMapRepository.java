package com.example.springboot.ssotSenderMap;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

import javax.transaction.Transactional;

@Repository
public interface SsotSenderMapRepository extends JpaRepository <SsotSenderMap, Long>{
    @Query("SELECT s FROM SsotSenderMap s WHERE s.userId =?1")
    Collection<SsotSenderMap> findAllByUserId(long userId);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM SsotSenderMap b WHERE b.userId = :userId")
    void deleteBySenderId(long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM SsotSenderMap b WHERE b.ssotId = :ssotId")
    void deleteBySsotId(long ssotId);
    
}
