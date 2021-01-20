package com.ubb.testing.tdd.Controllers;

import com.ubb.testing.tdd.Entities.Historial;
import com.ubb.testing.tdd.Exceptions.HistorialAlreadyExistException;
import com.ubb.testing.tdd.Exceptions.HistorialNotFoundException;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;

import com.ubb.testing.tdd.Services.HistorialService;
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

@RestController
@RequestMapping("/historial")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<Historial> findById(@PathVariable int id) {
        try {
            return new ResponseEntity<>(historialService.findById(id), HttpStatus.OK);
        } catch (HistorialNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/deleteById/{id}")
    public ResponseEntity<Void> deletePiso(@PathVariable Integer id) {
        try {
            historialService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HistorialNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/editHistorial")
    public ResponseEntity<Historial> editHabitacion(@RequestBody Historial historial) throws Exception {

        if (historial != null) {
            Historial aux = historialService.edit(historial);
            if (aux != null) {
                return new ResponseEntity<>(aux, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/historial")
    @ResponseStatus(HttpStatus.CREATED)
    public Historial createHistorial(@RequestBody Historial historial)
            throws HistorialNotFoundException, HistorialAlreadyExistException {

        try {

            historialService.save(historial);
            return historial;
        } catch (HistorialAlreadyExistException e) {
            return null;
        }

    }
}
