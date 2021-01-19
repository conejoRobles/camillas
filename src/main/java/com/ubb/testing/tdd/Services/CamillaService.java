package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Exceptions.CamillaAlreadyExistException;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;

import java.util.List;

public interface CamillaService {
    Camilla findById(int id) throws CamillaNotFoundException;
    
    Camilla save(Camilla camilla) throws CamillaNotFoundException, CamillaAlreadyExistException;
    
    List<Camilla> findAll();
}
