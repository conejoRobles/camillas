package com.ubb.testing.tdd.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.testing.tdd.Entities.Paciente;
import com.ubb.testing.tdd.Exceptions.PacienteNotFoundException;
import com.ubb.testing.tdd.Services.PacienteService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PacienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PacienteService pacienteService;

    private JacksonTester<Paciente> jsonPaciente;

    @InjectMocks
    private PacienteController pacienteController;

    Integer ID_PACIENTE_FIND = 1;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(pacienteController).build();
    }

    @Test
    void siSeInvocaPacienteFindByIdYExisteElPacienteDebeRetonarElPacienteEncontrado() throws Exception {
        //Given
        Paciente pacienteSearch = new Paciente(1, "19.321.344-3", "Pedro", "Herrera", "Habilitado");
        given(pacienteService.findById(ID_PACIENTE_FIND)).willReturn(pacienteSearch);

        //When
        MockHttpServletResponse response = mockMvc.perform(get("/paciente/findById/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonPaciente.write(pacienteSearch).getJson());
    }

    @Test
    void siSeInvocaPacienteFindByIdYNoExisteDebeRetonarStatusNotFound() throws Exception {
        //Given
        given(pacienteService.findById(ID_PACIENTE_FIND)).willThrow(new PacienteNotFoundException());

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/paciente/findById/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }
}
