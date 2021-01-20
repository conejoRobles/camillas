package com.ubb.testing.tdd.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.testing.tdd.Entities.Paciente;
import com.ubb.testing.tdd.Exceptions.PacienteAlreadyExistsException;
import com.ubb.testing.tdd.Exceptions.PacienteNotFoundException;
import com.ubb.testing.tdd.Services.PacienteService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;

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
        // Given
        Paciente pacienteSearch = new Paciente(1, "19.321.344-3", "Pedro", "Herrera", "Habilitado");
        given(pacienteService.findById(ID_PACIENTE_FIND)).willReturn(pacienteSearch);

        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/paciente/findById/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonPaciente.write(pacienteSearch).getJson());
    }

    @Test
    void siSeInvocaPacienteFindByIdYNoExisteDebeRetonarStatusNotFound() throws Exception {
        // Given
        given(pacienteService.findById(ID_PACIENTE_FIND)).willThrow(new PacienteNotFoundException());

        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/paciente/findById/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    void siSeInvocaCreatePacienteDebeRetornarLaRespuestaConElObjetoCreado() throws IOException, Exception {

        Paciente pacienteCreate = new Paciente(2, "19.090.005-3", "Rodrigo", "Cifuentes", "Habilitado");

        MockHttpServletResponse response = mockMvc.perform(post("/paciente/paciente")
                .contentType(MediaType.APPLICATION_JSON).content(jsonPaciente.write(pacienteCreate).getJson()))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.equals(pacienteCreate));
    }

    @Test
    void siSeInvocaCreatePacienteYElPacienteYaExisteDebeRetornarNotFound() throws Exception {

        Paciente pacienteCreate = new Paciente(2, "19.090.005-3", "Rodrigo", "Cifuentes", "Habilitado");

        doThrow(new PacienteAlreadyExistsException()).when(pacienteService).save(ArgumentMatchers.any(Paciente.class));

        MockHttpServletResponse response = mockMvc.perform(post("/api/paciente").contentType(MediaType.APPLICATION_JSON)
                .content(jsonPaciente.write(pacienteCreate).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

    @Test
    void siSeInvocaEditPacienteDebeRetornarLaRespuestaConElObjetoEditado() throws Exception {

        Paciente paciente = new Paciente(1, "19.321.344-3", "Pedro", "Herrera", "Habilitado");
        String newApellido = "Lopez";

        given(pacienteService.edit(any(paciente.getClass()))).willReturn(paciente);

        paciente.setApellido(newApellido);

        MockHttpServletResponse response = mockMvc.perform(post("/paciente/editPaciente")
                .contentType(MediaType.APPLICATION_JSON).content(jsonPaciente.write(paciente).getJson())).andReturn()
                .getResponse();

        assertThat(response.getContentAsString().equals(jsonPaciente.write(paciente).getJson()));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void siSeInvocaEditPacienteYEsteNoExisteDebeRetornarNotFound() throws Exception {
        Paciente paciente = new Paciente(1, "19.321.344-3", "Pedro", "Herrera", "Habilitado");
        String newApellido = "Lopez";

        given(pacienteService.edit(any(paciente.getClass()))).willReturn(null);

        paciente.setApellido(newApellido);

        MockHttpServletResponse response = mockMvc.perform(post("/paciente/editPaciente")
                .contentType(MediaType.APPLICATION_JSON).content(jsonPaciente.write(paciente).getJson())).andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
