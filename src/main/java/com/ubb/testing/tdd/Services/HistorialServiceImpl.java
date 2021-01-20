package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Historial;
import com.ubb.testing.tdd.Exceptions.HistorialAlreadyExistException;
import com.ubb.testing.tdd.Exceptions.HistorialNotFoundException;
import com.ubb.testing.tdd.Repository.HistorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HistorialServiceImpl implements HistorialService {

    @Autowired
    private HistorialRepository historialRepository;

    @Override
    @Transactional(readOnly = true)
    public Historial findById(int id) throws HistorialNotFoundException {
        Optional<Historial> historialRepositoryById = historialRepository.findById(id);
        if (historialRepositoryById.isPresent())
            return historialRepositoryById.get();
        else
            throw new HistorialNotFoundException();
    }

    @Override
    public void deleteById(int id) throws HistorialNotFoundException {
        Optional<Historial> historialRepositoryById = historialRepository.findById(id);
        if (historialRepositoryById.isPresent())
            historialRepository.deleteById(id);
        else
            throw new HistorialNotFoundException();
    }

    @Override
    public Historial edit(Historial historial) {
        Optional<Historial> aux = historialRepository.findById(historial.getId());
        if (aux != null) {
            return historialRepository.save(historial);
        }
        return null;

    }

	@Override
	public Historial save(Historial historial) throws HistorialAlreadyExistException, HistorialNotFoundException {
        return historialRepository.save(historial);
	}
}
