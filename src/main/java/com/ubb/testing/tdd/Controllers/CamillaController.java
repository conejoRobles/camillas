package com.ubb.testing.tdd.Controllers;

import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Exceptions.CamillaAlreadyExistException;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
import com.ubb.testing.tdd.Services.CamillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/camillas")
public class CamillaController {

    @Autowired
    private CamillaService camillaService;

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<Camilla> findById(@PathVariable int id) {
        try {
            return new ResponseEntity<>(camillaService.findById(id), HttpStatus.OK);
        } catch (CamillaNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<Camilla>> findAllPisos() {
        return new ResponseEntity<>(camillaService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/deleteById/{id}")
    public ResponseEntity<Void> deletePiso(@PathVariable Integer id) {
        try {
            camillaService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CamillaNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/editCamilla")
    public ResponseEntity<Camilla> editHabitacion(@RequestBody Camilla camilla) throws Exception {

        if (camilla != null) {
            Camilla aux = camillaService.edit(camilla);
            if (aux != null) {
                return new ResponseEntity<>(aux, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/save")
    public ResponseEntity<Camilla> saveCamilla(@RequestBody Camilla camilla) throws CamillaNotFoundException {
        try {
            camillaService.save(camilla);
            return new ResponseEntity<>(camilla, HttpStatus.CREATED);
        } catch (CamillaAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
