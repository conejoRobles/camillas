package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.PisoAlreadyExistsException;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;

import java.util.List;

public interface PisoService {

    List<Piso> findAll();

    Piso save(Piso piso) throws PisoNotFoundException, PisoAlreadyExistsException;

    Piso edit(Piso piso);

    Piso findById(Integer id) throws PisoNotFoundException;

    void deleteById(Integer id) throws PisoNotFoundException;


}
