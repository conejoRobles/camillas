package com.ubb.testing.tdd.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;
import com.ubb.testing.tdd.Exceptions.HabitacionNotFoundException;
import com.ubb.testing.tdd.Services.CamillaService;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CamillaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CamillaService camillaService;

    private JacksonTester<List<Camilla>> jsonListCamilla;

    private JacksonTester<Camilla> jsonCamilla;

    @InjectMocks
    private CamillaController camillaController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(camillaController).build();
    }

    @Test
    void siSeInvocaFindByIdYExisteCamillaDebeRetornarCamillaEncontrada() throws Exception {
        // Given
        Camilla camillaToFind = new Camilla(1, "Camilla Plegable", "Libre", 2020);
        given(camillaService.findById(1)).willReturn(camillaToFind);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/camillas/findById/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonCamilla.write(camillaToFind).getJson());
    }

    @Test
    void siSeInvocaFindByIdYNoExisteCamillaDebeRetonarStatusNotFound() throws Exception {
        // Given
        given(camillaService.findById(1)).willThrow(new CamillaNotFoundException());

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/camillas/findById/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    void siSeInvocaFindAllYExistenCamillasDebeRetornarLaListaDeCamillas() throws Exception {
        // Given
        List<Camilla> camillaList = new ArrayList<>();
        camillaList.add(new Camilla(1, "Camilla Plegable XL", "Libre", 2020));
        camillaList.add(new Camilla(2, "Camilla Plegable L", "Ocupada", 2018));
        camillaList.add(new Camilla(3, "Camilla Plegable M", "En mantencion", 2020));

        given(camillaService.findAll()).willReturn(camillaList);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/camillas/findAll").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonListCamilla.write(camillaList).getJson());
    }

    @Test
    void siSeInvocadeleteCamillaYNoExisteLaCamillaDebeLanzarStatusNotFound() throws Exception {
        // Given
        doThrow(new CamillaNotFoundException()).when(camillaService).deleteById(2);
        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/camillas/deleteById/2").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    void siSeInvocaDeleteCamillaYExisteDebePoderEliminarlo() throws Exception {
        // Given
        doNothing().when(camillaService).deleteById(2);
        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/camillas/deleteById/2").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEmpty();
    }
}
