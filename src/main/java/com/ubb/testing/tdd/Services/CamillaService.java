package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;

import java.util.List;

public interface CamillaService {
    Camilla findById(int id) throws CamillaNotFoundException;
    void save(Camilla camilla);
    List<Camilla> findAll();
}
