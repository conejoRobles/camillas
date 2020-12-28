package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Habitacion;
import com.ubb.testing.tdd.Exceptions.HabitacionAlreadyExistsException;
import com.ubb.testing.tdd.Exceptions.HabitacionNotFoundException;

import java.util.List;
import java.util.Optional;

public interface HabitacionService {

    List<Habitacion> findAll();

    Habitacion findById(Long id) throws HabitacionNotFoundException;

    Habitacion edit(Habitacion habitacion);

    Habitacion save(Habitacion habitacion) throws HabitacionAlreadyExistsException;

}
