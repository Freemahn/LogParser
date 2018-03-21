package com.freemahn.logparser.repository;

import com.freemahn.logparser.entity.BadIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Pavel Gordon
 */
@Repository
public interface BadIpRepository extends JpaRepository<BadIp, String> {


}