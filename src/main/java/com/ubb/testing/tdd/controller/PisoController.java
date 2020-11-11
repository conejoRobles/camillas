package com.ubb.testing.tdd.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubb.testing.tdd.Entities.Piso;

@RestController
@RequestMapping("/api")
public class PisoController {

	
	@PostMapping("/piso/")
	public ResponseEntity<?> createPiso(@RequestBody Piso piso){
		Piso pisoCreado = null;
		Map<String, Object> response = new HashMap<>();
		
		response.put("mensaje", "El producto ha sido creado con exito!");
		response.put("producto", pisoCreado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
