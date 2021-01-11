package com.ubb.testing.tdd.Repository;

import com.ubb.testing.tdd.Entities.Camilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CamillaRepository extends JpaRepository<Camilla, Integer> {
}
