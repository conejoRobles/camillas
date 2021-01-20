package com.ubb.testing.bdd.stepdefs;

import com.ubb.testing.bdd.CucumberSpringContextConfiguration;
import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.CamillaAlreadyExistException;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;
import com.ubb.testing.tdd.Exceptions.PisoAlreadyExistsException;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
import com.ubb.testing.tdd.Repository.CamillaRepository;
import com.ubb.testing.tdd.Repository.PisoRepository;
import com.ubb.testing.tdd.Services.CamillaService;
import com.ubb.testing.tdd.Services.PisoService;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class PisosStepDefs extends CucumberSpringContextConfiguration {

    @LocalServerPort
    private int port;

    private Piso piso;

    private ResponseEntity<List<Piso>> responsePisos;
    private ResponseEntity<Piso> responsePiso;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Autowired
    private PisoRepository pisoRepository;

    @Autowired
    private PisoService pisoService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @After
    public void teardown() {
        pisoRepository.deleteAll();
    }

    @Given("existe un piso; nombre {string}, estado {string}, numero de habitaciones {int}")
    public void existe_un_piso_nombre_estado_numero_de_habitaciones(String nombre, String estado, Integer nroHabitaciones) throws PisoNotFoundException, PisoAlreadyExistsException {
        piso = new Piso(nombre, estado, nroHabitaciones);
        pisoService.save(piso);
    }


    @When("solicito el piso que posee el id {int}")
    public void solicito_el_piso_que_posee_el_id(Integer idPiso) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responsePiso = testRestTemplate.exchange(createURLWithPort("/pisos/findById/" + idPiso),
                HttpMethod.GET, entity, Piso.class);
    }
    @Then("obtengo el estado Ok y el piso con id {int} y nombre {string}")
    public void obtengo_el_estado_ok_y_el_piso_con_id_y_nombre(Integer idPiso, String nombre) {
        assertEquals(HttpStatus.OK, responsePiso.getStatusCode());

        piso = responsePiso.getBody();

        assertNotNull(piso);
        assertEquals(idPiso, piso.getId());
        assertEquals(nombre, piso.getNombre());
    }

    @When("solicito todos los pisos")
    public void solicito_todos_los_pisos() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responsePisos = testRestTemplate.exchange(createURLWithPort("/pisos/findAll"), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<Piso>>() {
                });
    }

    @Then("obtengo el estado Ok y {int} pisos")
    public void obtengo_el_estado_ok_y_pisos(Integer nropisos) {
        assertEquals(HttpStatus.OK, responsePisos.getStatusCode());

        List<Piso> pisos = responsePisos.getBody();

        assertNotNull(pisos);
        assertEquals(nropisos, pisos.size());
    }

    @Given("existe un piso; id {int}, nombre {string}, estado {string}, numero de habitaciones {int}")
    public void existe_un_piso_id_nombre_estado_numero_de_habitaciones(Integer id, String nombre, String estado, Integer nroHabitaciones) throws PisoNotFoundException, PisoAlreadyExistsException {
        piso = new Piso(id,nombre, estado, nroHabitaciones);
        pisoService.save(piso);
    }


    @When("elimino un piso que posee el id {int}")
    public void elimino_un_piso_que_posee_el_id(Integer idPiso) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responsePiso = testRestTemplate.exchange(createURLWithPort("/pisos/deleteById/" + idPiso),
                HttpMethod.GET, entity, Piso.class);
    }
    @Then("obtengo es estado Ok y no lo encuentro el piso con la id {int}")
    public void obtengo_es_estado_ok_y_no_lo_encuentro_el_piso_con_la_id(Integer idPiso) {
        assertEquals(HttpStatus.OK, responsePiso.getStatusCode());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responsePiso = testRestTemplate.exchange(createURLWithPort("/pisos/findById/" + idPiso),
                HttpMethod.GET, entity, Piso.class);
        assertEquals(HttpStatus.NOT_FOUND, responsePiso.getStatusCode());
    }


    @When("edito el piso que posee el id {int}, cambiando el estado a {string}")
    public void edito_el_piso_que_posee_el_id_cambiando_el_estado_a(Integer idPiso, String newEstado) {
        piso.setEstado(newEstado);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<Piso> entity = new HttpEntity<>(piso, httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responsePiso = testRestTemplate.exchange(createURLWithPort("/pisos/editPiso"), HttpMethod.POST, entity,
                Piso.class);
    }
    @Then("obtengo el estado Ok y el piso con estado {string}")
    public void obtengo_el_estado_ok_y_el_piso_con_estado(String newEstado) {
        piso = responsePiso.getBody();

        assertEquals(HttpStatus.OK, responsePiso.getStatusCode());
        assertEquals(newEstado, piso.getEstado());
    }

    @When("solicito se agregue un piso al hospital")
    public void solicito_se_agregue_un_piso_al_hospital() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<Piso> entity = new HttpEntity<>(piso,httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responsePiso = testRestTemplate
                .exchange(createURLWithPort("/pisos/save"),HttpMethod.POST,entity,Piso.class);
    }
    @Then("obtengo el estado {string} y el piso agregado tiene como nombre {string} y numero de habitaciones {int}")
    public void obtengo_el_estado_y_el_piso_agregado_tiene_como_nombre_y_numero_de_habitaciones(String estado, String nombre, Integer nroHabitaciones) {
        assertEquals(estado.toUpperCase(), responsePiso.getStatusCode().name().toString());

        System.out.println(responsePiso.getBody().getNombre());
        piso = responsePiso.getBody();

        assertNotNull(piso);
        assertEquals(nombre,piso.getNombre());
        assertEquals(nroHabitaciones,piso.getNroHabitaciones());
    }

}
