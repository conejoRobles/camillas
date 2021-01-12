package com.ubb.testing.tdd.Repository;

import com.ubb.testing.tdd.Entities.Historial;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface HistorialRepository extends JpaRepository<Historial, Integer> {
}
