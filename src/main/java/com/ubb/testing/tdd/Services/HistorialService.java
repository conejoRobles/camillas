package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Historial;
import com.ubb.testing.tdd.Exceptions.HistorialNotFoundException;

public interface HistorialService {
    Historial findById(int id) throws HistorialNotFoundException;
}
