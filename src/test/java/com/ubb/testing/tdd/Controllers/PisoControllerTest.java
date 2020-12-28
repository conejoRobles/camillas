package com.ubb.testing.tdd.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
import com.ubb.testing.tdd.Services.PisoService;
import com.ubb.testing.tdd.Services.PisoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class PisoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PisoService pisoService;

    private JacksonTester<List<Piso>> jsonListPiso;

    private JacksonTester<Piso> jsonPiso;

    @InjectMocks
    private PisoController pisoController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(pisoController).build();
    }

    @Test
    void siSeInvocaFindAllPisosYExistenPisosHabilitadosDebeRetornarListaConLosPisos() throws Exception {
        // Given
        ArrayList<Piso> pisosFromService = new ArrayList<>();
        pisosFromService.add(new Piso(1, "Piso 1", "Habilitado", 2));
        pisosFromService.add(new Piso(2, "Piso 2", "Ocupado", 1));
        pisosFromService.add(new Piso(3, "Piso 3", "En preparacion", 1));

        given(pisoService.findAll()).willReturn(pisosFromService);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/pisos/findAll").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonListPiso.write(pisosFromService).getJson());
    }

    @Test
    void siSeInvocalFindAllPisosYNoExistenPisosHabilitadosDebeRetornarListaVacia() throws Exception {
        ArrayList<Piso> pisoFromService = new ArrayList<>();
        given(pisoService.findAll()).willReturn(pisoFromService);

        MockHttpServletResponse response = mockMvc.perform(get("/pisos/findAll").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonListPiso.write(pisoFromService).getJson());
    }

    @Test
    void siSeInvocaPisoFindByIdYExisteElPisoDebeRetonarElPisoEncontrado() throws Exception {
        // Given
        Piso pisoFromService = new Piso(1, "Piso 1", "Habilitado", 2);
        given(pisoService.findById(1)).willReturn(pisoFromService);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/pisos/findById/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonPiso.write(pisoFromService).getJson());
    }

    @Test
    void siSeInvocaPisoFindByIdYNoExisteDebeRetonarStatusNotFound() throws Exception {
        // Given
        given(pisoService.findById(1)).willThrow(new PisoNotFoundException());

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/pisos/findById/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    void siSeInvocaCreatePisoDebeRetornarLaRespuestaConElObjetoCreado() throws Exception {
        // given

        Piso pisoCreate = new Piso(1, "Piso 1", "Habilitado", 25);
        MockHttpServletResponse response = mockMvc.perform(post("/pisos/piso").contentType(MediaType.APPLICATION_JSON)
                .content(jsonPiso.write(pisoCreate).getJson())).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.equals(pisoCreate));
    }

    @Test
    void siSeInvocaEditPisoDebeRetornarLaRespuestaConElObjetoEditado() throws Exception {
        // given

        Piso piso = new Piso(1, "Piso 1", "Habilitado", 25);

        String nuevoNombre = "Primer Piso";
        String nuevoEstado = "Deshabilitado";
        int nuevoNumeroDeHabitaciones = 20;

        piso.setNombre(nuevoNombre);
        piso.setEstado(nuevoEstado);
        piso.setNroHabitaciones(nuevoNumeroDeHabitaciones);

        MockHttpServletResponse response = mockMvc.perform(
                post("/pisos/editPiso").contentType(MediaType.APPLICATION_JSON).content(jsonPiso.write(piso).getJson()))
                .andReturn().getResponse();

        assertThat(response.equals(piso));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void siSeInvocadeletePisoYNoExisteElPisoDebeLanzarStatusNotFound() throws Exception {
        //Given
        doThrow(new PisoNotFoundException()).when(pisoService).deleteById(1);
        //When
        MockHttpServletResponse response = mockMvc.perform(
                get("/pisos/deleteById/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    void siSeInvocaDeletePisoYExisteDebePoderEliminarlo() throws Exception {
        //Given
        doNothing().when(pisoService).deleteById(1);
        //When
        MockHttpServletResponse response = mockMvc.perform(
                get("/pisos/deleteById/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEmpty();
    }
}
