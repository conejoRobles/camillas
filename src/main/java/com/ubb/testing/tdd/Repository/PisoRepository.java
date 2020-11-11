package com.ubb.testing.tdd.Repository;

import com.ubb.testing.tdd.Entities.Piso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PisoRepository extends JpaRepository<Piso, Long> {
}
