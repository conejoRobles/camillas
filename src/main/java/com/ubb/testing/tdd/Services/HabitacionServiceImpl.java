package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Habitacion;
import com.ubb.testing.tdd.Exceptions.HabitacionAlreadyExistsException;
import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.HabitacionNotFoundException;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
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
        if (habitacionFromDb.isPresent())
            return habitacionFromDb.get();
        else
            throw new HabitacionNotFoundException();
    }

    @Override
    public Habitacion edit(Habitacion habitacion) {
        if (habitacionRepository.findById(habitacion.getId()) != null) {
            return habitacionRepository.save(habitacion);
        }
        return null;
    }

    @Override
    public Habitacion save(Habitacion habitacion) throws HabitacionAlreadyExistsException {
        if (habitacionRepository.findById(habitacion.getId()) == null) {
            return habitacionRepository.save(habitacion);
        } else {
            throw new HabitacionAlreadyExistsException();
        }

    }

    public void deleteById(Long id) throws HabitacionNotFoundException {
        Optional<Habitacion> habitacionFromDB = habitacionRepository.findById(id);
        if (habitacionFromDB.isPresent()) {
            habitacionRepository.deleteById(id);
        } else {
            throw new HabitacionNotFoundException();
        }
    }
}
