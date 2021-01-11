package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;
import com.ubb.testing.tdd.Repository.CamillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CamillaServiceImpl implements CamillaService {

    @Autowired
    private CamillaRepository camillaRepository;

    @Override
    @Transactional(readOnly = true)
    public Camilla findById(int id) throws CamillaNotFoundException {
        Optional<Camilla> camillaFromDb = camillaRepository.findById(id);
        if (camillaFromDb.isPresent()) return camillaFromDb.get();
        else throw new CamillaNotFoundException();
    }

}
