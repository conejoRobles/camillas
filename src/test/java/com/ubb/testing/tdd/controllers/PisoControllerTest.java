package com.ubb.testing.tdd.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class PisoControllerTest {

	private MockMvc mockMvc;

	@Mock
	private PisoServiceImpl pisoService;

	@InjectMocks
	private PisoController pisoController;

	private JacksonTester<Piso> jsonPiso;

	@BeforeEach
	void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(pisoController).build();
	}

	@Test
	void siSeInvocaCreatePisoDebeRetornarLaRespuestaConElObjetoCreado() throws Exception {
		// given

		Piso pisoCreate = new Piso("Piso 1", "Habilitado", 25);
		MockHttpServletResponse response = mockMvc.perform(
				post("/api/piso").contentType(MediaType.APPLICATION_JSON).content(jsonPiso.write(pisoCreate).getJson()))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.equals(pisoCreate));
	}

//	@Test()
//	void siSeInvocaCreatePisoYElPisoYaExisteDebeLanzarLaExcepcion() throws Exception {
//		// given
//
//		Piso pisoCreate1 = new Piso("", "", 0);
//	
//
//		
//	
//		String exception = mockMvc.perform(
//				post("/api/piso").contentType(MediaType.APPLICATION_JSON).content(jsonPiso.write(pisoCreate1).getJson()))
//				.andReturn().getResolvedException().getMessage();
//		
//		assertEquals("El piso no debe ser vacio", exception);
//		
//
//	}

}
