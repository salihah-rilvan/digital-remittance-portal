package com.example.springboot.ssotReceiverMap;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

import javax.transaction.Transactional;

@Repository
public interface SsotReceiverMapRepository extends JpaRepository <SsotReceiverMap, Long>{
    @Query("SELECT r FROM SsotReceiverMap r WHERE r.receiverId = ?1")
    Collection<SsotReceiverMap> findReceiverById(long receiverId);

    @Query("SELECT r FROM SsotReceiverMap r INNER JOIN Ssot s ON r.ssotId = s.ssotId WHERE s.ssotName = :ssotName AND r.receiverId = :receiverId")
    SsotReceiverMap findSsotReceiverMapBySsotIdAndReceiverId(@Param ("receiverId") long receiverId, @Param("ssotName") String ssotName);

    @Transactional
    @Modifying
    @Query("DELETE FROM SsotReceiverMap b WHERE b.ssotId = :ssotId")
    void deleteBySsotId(long ssotId);
}



