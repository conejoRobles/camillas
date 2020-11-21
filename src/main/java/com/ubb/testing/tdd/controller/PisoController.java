package com.ubb.testing.tdd.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Services.PisoService;

@RestController
@RequestMapping("/api")
public class PisoController {

	@Autowired
	private PisoService pisoService;
	
	@PostMapping("/piso")
	@ResponseStatus(HttpStatus.CREATED)
	public Piso createPiso(@RequestBody Piso piso) throws Exception{
		try {
			pisoService.save(piso);
		} catch(DataAccessException e){
			return null;
		}
		
		return piso;
	}
}
