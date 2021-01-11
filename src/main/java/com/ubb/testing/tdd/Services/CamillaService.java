package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;

public interface CamillaService {
    Camilla findById(int id) throws CamillaNotFoundException;
}
