package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
import com.ubb.testing.tdd.Repository.PisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PisoServiceImpl implements PisoService {

    @Autowired
    private PisoRepository pisoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Piso> findAll() {
        return pisoRepository.findAll();
    }

	@Override
	public Piso save(Piso piso) {
		return pisoRepository.save(piso);
	}
	
	@Override
    @Transactional(readOnly = true)
    public Piso findById(long id) {
        return pisoRepository.findById(id).orElse(null);
    }
}
