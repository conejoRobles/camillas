package com.ubb.testing.tdd.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.testing.tdd.Entities.Historial;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;
import com.ubb.testing.tdd.Exceptions.HabitacionNotFoundException;
import com.ubb.testing.tdd.Exceptions.HistorialAlreadyExistException;
import com.ubb.testing.tdd.Exceptions.HistorialNotFoundException;
import com.ubb.testing.tdd.Services.HistorialService;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class HistorialControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HistorialService historialService;

    private JacksonTester<Historial> jsonHistorial;

    @InjectMocks
    private HistorialController historialController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(historialController).build();
    }

    @Test
    void siSeInvocaFindByIdYExisteHistorialDebeRetornarHistorialEncontrado() throws Exception {
        // Given
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");
        Date fecIngreso = objSDF.parse("20-01-2021");
        Date fecSalida = objSDF.parse("25-01-2021");

        Historial historialToFind = new Historial(1, fecIngreso, fecSalida);
        given(historialService.findById(1)).willReturn(historialToFind);

        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/historial/findById/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonHistorial.write(historialToFind).getJson());
    }

    @Test
    void siSeInvocaFindByIdYNoExisteHistorialDebeRetonarStatusNotFound() throws Exception {
        // Given
        given(historialService.findById(1)).willThrow(new HistorialNotFoundException());

        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/camillas/findById/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    void siSeInvocadeleteHistorialYNoExisteLaCamillaDebeLanzarStatusNotFound() throws Exception {
        // Given
        doThrow(new HistorialNotFoundException()).when(historialService).deleteById(2);
        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/historial/deleteById/2").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    void siSeInvocaDeleteHistorialYExisteDebePoderEliminarlo() throws Exception {
        // Given
        doNothing().when(historialService).deleteById(2);
        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/historial/deleteById/2").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    void siSeInvocaEditHistorialDebeRetornarLaRespuestaConElObjetoEditado() throws Exception {
        // Given
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");
        Date fecIngreso = objSDF.parse("20-01-2021");
        Date fecSalida = objSDF.parse("25-01-2021");
        Date newFecIngreso = objSDF.parse("22-01-2021");

        Historial historial = new Historial(1, fecIngreso, fecSalida);

        given(historialService.edit(any(historial.getClass()))).willReturn(historial);

        historial.setFechaIngreso(newFecIngreso);

        MockHttpServletResponse response = mockMvc.perform(post("/historial/editHistorial")
                .contentType(MediaType.APPLICATION_JSON).content(jsonHistorial.write(historial).getJson())).andReturn()
                .getResponse();

        assertThat(response.getContentAsString().equals(jsonHistorial.write(historial).getJson()));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void siSeInvocaEditHistorialYEsteNoExisteDebeRetornarNotFound() throws Exception {
        // Given
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");
        Date fecIngreso = objSDF.parse("20-01-2021");
        Date fecSalida = objSDF.parse("25-01-2021");
        Date newFecIngreso = objSDF.parse("22-01-2021");

        Historial historial = new Historial(1, fecIngreso, fecSalida);

        given(historialService.edit(any(historial.getClass()))).willReturn(null);

        historial.setFechaIngreso(newFecIngreso);

        MockHttpServletResponse response = mockMvc.perform(post("/historial/editHistorial")
                .contentType(MediaType.APPLICATION_JSON).content(jsonHistorial.write(historial).getJson())).andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void siSeInvocaCreateHistorialDebeRetornarLaRespuestaConElObjetoCreado() throws IOException, Exception {
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");

        Date fecIngreso = objSDF.parse("25-01-2021");
        Date fecSalida = objSDF.parse("02-02-2021");

        Historial historialSave = new Historial(1, fecIngreso, fecSalida);

        MockHttpServletResponse response = mockMvc.perform(post("/historial/historial")
                .contentType(MediaType.APPLICATION_JSON).content(jsonHistorial.write(historialSave).getJson()))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.equals(historialSave));
    }

    @Test
    void siSeInvocaCreateHistorialYElHistorialYaExisteDebeRetornarNotFound() throws Exception {
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");

        Date fecIngreso = objSDF.parse("25-01-2021");
        Date fecSalida = objSDF.parse("02-02-2021");

        Historial historialSave = new Historial(1, fecIngreso, fecSalida);
        doThrow(new HistorialAlreadyExistException()).when(historialService)
                .save(ArgumentMatchers.any(Historial.class));

        MockHttpServletResponse response = mockMvc.perform(post("/api/historial")
                .contentType(MediaType.APPLICATION_JSON).content(jsonHistorial.write(historialSave).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }
}
