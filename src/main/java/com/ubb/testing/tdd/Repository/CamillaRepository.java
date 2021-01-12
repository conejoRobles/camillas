package com.ubb.testing.tdd.Repository;

import com.ubb.testing.tdd.Entities.Camilla;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface CamillaRepository extends JpaRepository<Camilla, Integer> {
}
