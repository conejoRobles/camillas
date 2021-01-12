package com.ubb.testing.tdd.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Entities.Historial;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;
import com.ubb.testing.tdd.Exceptions.HistorialNotFoundException;
import com.ubb.testing.tdd.Services.HistorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
    void siSeInvocaFindByIdYExisteCamillaDebeRetornarCamillaEncontrada() throws Exception {
        // Given
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");
        Date fecIngreso = objSDF.parse("20-01-2021");
        Date fecSalida = objSDF.parse("25-01-2021");

        Historial historialToFind = new Historial(1, fecIngreso, fecSalida);
        given(historialService.findById(1)).willReturn(historialToFind);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/historial/findById/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonHistorial.write(historialToFind).getJson());
    }

    @Test
    void siSeInvocaFindByIdYNoExisteCamillaDebeRetonarStatusNotFound() throws Exception {
        // Given
        given(historialService.findById(1)).willThrow(new HistorialNotFoundException());

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/camillas/findById/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }
}
