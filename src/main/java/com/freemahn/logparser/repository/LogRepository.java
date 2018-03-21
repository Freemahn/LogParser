package com.freemahn.logparser.repository;

import com.freemahn.logparser.entity.BadIp;
import com.freemahn.logparser.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Pavel Gordon
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

//    List<Log> findAllByIp(String ip);
//
//    List<Log> findAllByIpAndDatetimeBetween(String ip, LocalDateTime start, LocalDateTime end);

//
    @Query("SELECT new com.freemahn.logparser.entity.BadIp(ip, count(ip)) FROM Log WHERE datetime BETWEEN :start AND :end GROUP BY ip HAVING count(ip) > :threshold")
    List<BadIp> findIpsBetweenDatesWithThreshold(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("threshold") Long threshold);

}