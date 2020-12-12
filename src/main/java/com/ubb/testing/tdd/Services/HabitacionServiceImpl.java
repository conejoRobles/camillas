package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Habitacion;
import com.ubb.testing.tdd.Exceptions.HabitacionNotFoundException;
import com.ubb.testing.tdd.Repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionServiceImpl implements HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Override
    public List<Habitacion> findAll() {
        return habitacionRepository.findAll();
    }

    @Override
    public Habitacion findById(Long id) throws HabitacionNotFoundException {
        Optional<Habitacion> habitacionFromDb = habitacionRepository.findById(id);
        if (habitacionFromDb.isPresent()) return habitacionFromDb.get();
        else throw new HabitacionNotFoundException();
    }
}
