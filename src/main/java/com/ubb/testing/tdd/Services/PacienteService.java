package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Paciente;
import com.ubb.testing.tdd.Exceptions.PacienteAlreadyExistsException;
import com.ubb.testing.tdd.Exceptions.PacienteNotFoundException;

public interface PacienteService {

    Paciente findById(int id) throws PacienteNotFoundException;
    
    Paciente save(Paciente paciente) throws PacienteNotFoundException, PacienteAlreadyExistsException;
}
