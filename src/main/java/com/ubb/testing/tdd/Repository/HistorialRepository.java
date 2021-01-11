package com.ubb.testing.tdd.Repository;

import com.ubb.testing.tdd.Entities.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialRepository extends JpaRepository<Historial, Integer> {
}
