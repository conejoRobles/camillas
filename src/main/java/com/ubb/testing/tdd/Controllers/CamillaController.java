package com.ubb.testing.tdd.Controllers;

import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;
import com.ubb.testing.tdd.Services.CamillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
