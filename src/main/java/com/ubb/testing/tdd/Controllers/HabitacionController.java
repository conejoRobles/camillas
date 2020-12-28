package com.ubb.testing.tdd.Controllers;

import com.ubb.testing.tdd.Entities.Habitacion;
import com.ubb.testing.tdd.Exceptions.HabitacionNotFoundException;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
import com.ubb.testing.tdd.Services.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<Habitacion>> findAllPisos() {
        return new ResponseEntity<>(habitacionService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<Habitacion> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(habitacionService.findById(id), HttpStatus.OK);
        } catch (HabitacionNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/deleteById/{id}")
    public ResponseEntity<Void> deletePiso(@PathVariable long id){
        try {
            habitacionService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (HabitacionNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
