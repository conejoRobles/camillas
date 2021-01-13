package com.ubb.testing.tdd.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.testing.tdd.Entities.Habitacion;
import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.HabitacionAlreadyExistsException;
import com.ubb.testing.tdd.Exceptions.HabitacionNotFoundException;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
import com.ubb.testing.tdd.Services.HabitacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class HabitacionControllerTest {

	private MockMvc mockMvc;

	@Mock
	private HabitacionService habitacionService;

	private JacksonTester<List<Habitacion>> jsonListHabitaciones;

	private JacksonTester<Habitacion> jsonHabitacion;

	@InjectMocks
	private HabitacionController habitacionController;

	@BeforeEach
	void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(habitacionController).build();
	}

	@Test
	public void siSeInovaElMetodoFindAllYExisteHabitacionesDevuelveUnaListaConLasHabitaciones() throws Exception {
		// Given
		ArrayList<Habitacion> habitacionesFromService = new ArrayList<>();
		habitacionesFromService.add(new Habitacion(1, "Cardiologia", "Disponible", 2));
		habitacionesFromService.add(new Habitacion(2, "Pediatria", "Disponible", 3));
		habitacionesFromService.add(new Habitacion(3, "UCI", "Disponible", 6));
		habitacionesFromService.add(new Habitacion(4, "Urgencias", "Disponible", 5));
		given(habitacionService.findAll()).willReturn(habitacionesFromService);

		// When
		MockHttpServletResponse response = mockMvc.perform(get("/habitaciones/findAll").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonListHabitaciones.write(habitacionesFromService).getJson());

	}

	@Test
	void siSeInvocaFindByIdYExisteUnaHabitacionDebeRetornarLaHabitacionEncontrada() throws Exception {
		// Given
		Habitacion habitacionFromService = new Habitacion(3, "UCI", "Disponible", 6);
		given(habitacionService.findById(3L)).willReturn(habitacionFromService);

		// When
		MockHttpServletResponse response = mockMvc.perform(get("/habitaciones/findById/3").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonHabitacion.write(habitacionFromService).getJson());
	}

	@Test
	void siSeInvocaEditHabitacionDebeRetornarLaRespuestaConElObjetoEditado() throws Exception {
		// given

		Habitacion habitacion = new Habitacion(3, "UCI", "Disponible", 6);
		String nuevaEspecialidad = "Cardiologia";
		int nuevoNumeroCamas = 20;

		habitacion.setEspecialidad(nuevaEspecialidad);
		habitacion.setNroCamasMax(nuevoNumeroCamas);

		given(habitacionService.edit(any(habitacion.getClass()))).willReturn(habitacion);

		MockHttpServletResponse response = mockMvc.perform(post("/habitaciones/editHabitacion")
				.contentType(MediaType.APPLICATION_JSON).content(jsonHabitacion.write(habitacion).getJson())).andReturn()
				.getResponse();

		assertThat(response.getContentAsString().equals(jsonHabitacion.write(habitacion).getJson()));
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void siSeInvocaSaveHabitacionDebeRetornarLaHabitacionObtenida() throws Exception {

		Habitacion habitacionCreate = new Habitacion(50, "Radiologia", "Disponible", 1);
		MockHttpServletResponse response = mockMvc.perform(post("/habitaciones/save").contentType(MediaType.APPLICATION_JSON)
				.content(jsonHabitacion.write(habitacionCreate).getJson())).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonHabitacion.write(habitacionCreate).getJson());
	}

	@Test
	void siSeInvocaSaveHabitacionDebeRetornarStatusOk() throws Exception {

		Habitacion habitacionCreate = new Habitacion(50, "Radiologia", "Disponible", 1);

		doThrow(new HabitacionAlreadyExistsException()).when(habitacionService).save(ArgumentMatchers.any(Habitacion.class));

		MockHttpServletResponse response = mockMvc.perform(post("/habitaciones/save").contentType(MediaType.APPLICATION_JSON)
				.content(jsonHabitacion.write(habitacionCreate).getJson())).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void siSeInvocadeletePisoYNoExisteElPisoDebeLanzarStatusNotFound() throws Exception {
		// Given
		long id = 2;
		doThrow(new HabitacionNotFoundException()).when(habitacionService).deleteById(id);
		// When
		MockHttpServletResponse response = mockMvc
				.perform(get("/habitaciones/deleteById/2").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(response.getContentAsString()).isEmpty();
	}

	@Test
	void siSeInvocaDeletePisoYExisteDebePoderEliminarlo() throws Exception {
		// Given
		long id = 2;
		doNothing().when(habitacionService).deleteById(id);
		// When
		MockHttpServletResponse response = mockMvc
				.perform(get("/habitaciones/deleteById/2").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEmpty();
	}

	@Test
	void siSeInvocaEditHabitacionYNoExisteDebeRetornarNotFound() throws Exception {
		// given

		Habitacion habitacion = new Habitacion(3, "UCI", "Disponible", 6);
		String nuevaEspecialidad = "Cardiologia";
		int nuevoNumeroCamas = 20;

		habitacion.setEspecialidad(nuevaEspecialidad);
		habitacion.setNroCamasMax(nuevoNumeroCamas);

		given(habitacionService.edit(any(habitacion.getClass()))).willReturn(null);

		MockHttpServletResponse response = mockMvc.perform(post("/habitaciones/editHabitacion")
				.contentType(MediaType.APPLICATION_JSON).content(jsonHabitacion.write(habitacion).getJson())).andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

}
