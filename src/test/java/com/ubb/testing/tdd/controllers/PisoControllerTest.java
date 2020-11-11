package com.ubb.testing.tdd.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.testing.tdd.Entities.Piso;

import com.ubb.testing.tdd.Services.PisoServiceImpl;
import com.ubb.testing.tdd.controller.PisoController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(MockitoExtension.class)
public class PisoControllerTest {

	
	private MockMvc mockMvc;
	
	@Mock
	private PisoServiceImpl pisoService;
	
    private JacksonTester<Piso> jsonPiso;
	
	@InjectMocks
	private PisoController pisoController;
	
	
	@BeforeEach
	void setup() {
		 JacksonTester.initFields(this, new ObjectMapper());
	        mockMvc = MockMvcBuilders.standaloneSetup(pisoController)
	                			 .build();
	}
	
	@Test
	void siSeInvocaCreatePisoDebeRetornarLaRespuestaConElObjetoCreado() throws Exception{
		//given
		Piso pisoCreate= new Piso("Piso 1", "Habilitado", 25);
		given(pisoService.save(pisoCreate)).willReturn(pisoCreate);
		
		ObjectMapper mapper = new ObjectMapper();
		 String newPisoAsJSON = mapper.writeValueAsString(pisoCreate);
		
		this.mockMvc.perform(post("/api/piso/")
			.content(newPisoAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		//en primera instancia devuelve un 404 porque la funcion del crear piso no esta creada
				
	}
	
}
