package com.ubb.testing.tdd.Controllers;

import com.ubb.testing.tdd.Entities.Paciente;
import com.ubb.testing.tdd.Exceptions.PacienteAlreadyExistsException;
import com.ubb.testing.tdd.Exceptions.PacienteNotFoundException;
import com.ubb.testing.tdd.Services.PacienteService;
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
@RequestMapping("/paciente")
public class PacienteController {

	@Autowired
	private PacienteService pacienteService;

	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<Paciente> findById(@PathVariable Integer id) {
		try {
			return new ResponseEntity<>(pacienteService.findById(id), HttpStatus.OK);
		} catch (PacienteNotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/paciente")
	@ResponseStatus(HttpStatus.CREATED)
	public Paciente createPaciente(@RequestBody Paciente paciente)
			throws PacienteNotFoundException, PacienteAlreadyExistsException {

		try {
			
			pacienteService.save(paciente);
			return paciente;
		} catch (PacienteAlreadyExistsException e) {
			return null;
		}

	}
}
