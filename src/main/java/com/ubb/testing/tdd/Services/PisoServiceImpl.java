package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.PisoAlreadyExistsException;
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
    @Transactional(readOnly = true)
    public Piso findById(Integer id) throws PisoNotFoundException {
        if (pisoRepository.findById(id).orElse(null) != null) {
            return pisoRepository.findById(id).get();
        } else {
            throw new PisoNotFoundException();
        }
    }

    @Override
    public Piso save(Piso piso) throws PisoNotFoundException, PisoAlreadyExistsException{
    	
    	if(findById(piso.getId()) == null) {
    		System.out.println("te encontré");
    		   return pisoRepository.save(piso);
    	}else {
    		throw new PisoAlreadyExistsException();
    	}
     
    }

    @Override
    public Piso edit(Piso piso) {
        if (pisoRepository.findById(piso.getId()) != null) {
            return pisoRepository.save(piso);
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) throws PisoNotFoundException {
        Optional<Piso> pisoFromDB = pisoRepository.findById(id);
        if(pisoFromDB.isPresent()){
            pisoRepository.deleteById(id);
        }else{
            throw new PisoNotFoundException();
        }
    }
}
