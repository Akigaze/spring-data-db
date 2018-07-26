package com.spring.data.db.one.to.one.repositories;

import com.spring.data.db.one.to.one.entities.Leader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderRepository extends JpaRepository<Leader,Long> {
}
