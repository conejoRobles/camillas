package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PisoService {

    List<Piso> findAll();
    
    Piso save(Piso piso);

	Piso findById(long id);

    Piso findById(long id) throws PisoNotFoundException;

}
