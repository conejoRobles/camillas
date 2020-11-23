package com.ubb.testing.tdd.Controllers;

import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
import com.ubb.testing.tdd.Services.PisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.NestedServletException;

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
        try {
            return new ResponseEntity<>(pisoService.findById(id), HttpStatus.OK);
        } catch (PisoNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/piso")
    @ResponseStatus(HttpStatus.CREATED)
    public Piso createPiso(@RequestBody Piso piso) throws Exception {

        if (piso.getNombre().equals("") || piso.getEstado().equals("") || piso.getNroHabitaciones() == 0) {
            throw new NestedServletException("El piso no debe ser vacio");
        }
        try {
            pisoService.save(piso);
        } catch (DataAccessException e) {
            return null;
        }

        return piso;
    }

    @PostMapping("/editPiso")
    @ResponseStatus(HttpStatus.OK)
    public Piso editPiso(@RequestBody Piso piso) throws Exception {

        if (piso != null) {
            if (pisoService.edit(piso) != null) {
                return pisoService.edit(piso);
            }
        }
        return null;
    }
}
