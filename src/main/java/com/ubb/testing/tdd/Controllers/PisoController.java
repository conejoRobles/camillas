package com.ubb.testing.tdd.Controllers;

import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.PisoAlreadyExistsException;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
import com.ubb.testing.tdd.Services.PisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pisos")
public class PisoController {

    @Autowired
    private PisoService pisoService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<Piso>> findAllPisos() {
        return new ResponseEntity<>(pisoService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<Piso> findById(@PathVariable Integer id) {
        System.out.println("id:"+id);
        try {
            return new ResponseEntity<>(pisoService.findById(id), HttpStatus.OK);
        } catch (PisoNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Piso createPiso(@RequestBody Piso piso) throws PisoAlreadyExistsException, PisoNotFoundException {

     
        try {
        	
            pisoService.save(piso);
            return piso;
        } catch (PisoAlreadyExistsException e) {
            return null;
        }

       
    }

    @PostMapping("/editPiso")
    public ResponseEntity<Piso> editPiso(@RequestBody Piso piso) throws Exception {

        if (piso != null) {
            Piso aux = pisoService.edit(piso);
            if (aux != null) {
                return new ResponseEntity<>(aux, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/deleteById/{id}")
    public ResponseEntity<Void> deletePiso(@PathVariable Integer id){
        System.out.println("delete:"+id);
        try {
            pisoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (PisoNotFoundException e){
            System.out.println("entre aqui");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
