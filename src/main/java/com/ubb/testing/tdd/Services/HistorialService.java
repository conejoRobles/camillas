package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Historial;
import com.ubb.testing.tdd.Exceptions.HistorialAlreadyExistException;
import com.ubb.testing.tdd.Exceptions.HistorialNotFoundException;

public interface HistorialService {
    Historial findById(int id) throws HistorialNotFoundException;

    void deleteById(int id) throws HistorialNotFoundException;

    Historial edit(Historial historial);

    Historial save(Historial historial) throws HistorialAlreadyExistException, HistorialNotFoundException;
}
