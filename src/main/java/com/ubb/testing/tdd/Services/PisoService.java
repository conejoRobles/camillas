package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PisoService {

    List<Piso> findAll();

    Piso save(Piso piso);

    Piso edit(Piso piso);

    Piso findById(Integer id) throws PisoNotFoundException;

    void deleteById(Integer id) throws PisoNotFoundException;



}
