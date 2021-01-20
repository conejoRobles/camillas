package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Paciente;
import com.ubb.testing.tdd.Exceptions.PacienteAlreadyExistsException;
import com.ubb.testing.tdd.Exceptions.PacienteNotFoundException;
import com.ubb.testing.tdd.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    @Transactional(readOnly = true)
    public Paciente findById(int id) throws PacienteNotFoundException {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent())
            return pacienteOptional.get();
        else
            throw new PacienteNotFoundException();
    }

    @Override
    public Paciente save(Paciente paciente) throws PacienteNotFoundException, PacienteAlreadyExistsException {
        if (findById(paciente.getId()) == null) {
            return pacienteRepository.save(paciente);
        } else {
            throw new PacienteAlreadyExistsException();
        }
    }

    @Override
    public Paciente edit(Paciente paciente) {
        Optional<Paciente> aux = pacienteRepository.findById(paciente.getId());
        if (aux != null) {
            return pacienteRepository.save(paciente);
        }
        return null;

    }

    @Override
    public void deleteById(int id) throws PacienteNotFoundException {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent())
            pacienteRepository.deleteById(id);
        else
            throw new PacienteNotFoundException();
    }
}
