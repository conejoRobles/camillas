package com.ubb.testing.bdd.stepdefs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ubb.testing.bdd.CucumberSpringContextConfiguration;
import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Exceptions.CamillaAlreadyExistException;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;
import com.ubb.testing.tdd.Repository.CamillaRepository;
import com.ubb.testing.tdd.Services.CamillaService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import io.cucumber.java.After;

import java.util.List;

public class CamillasStepDefs extends CucumberSpringContextConfiguration {

    @LocalServerPort
    private int port;

    private Camilla camilla;

    private ResponseEntity<List<Camilla>> responseCamillas;
    private ResponseEntity<Camilla> responseCamilla;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Autowired
    private CamillaRepository camillaRepository;

    @Autowired
    private CamillaService camillaService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @After
    public void teardown() {
        camillaRepository.deleteAll();
    }

    @Given("existe una camilla; tipo {string}, estado {string}, year {int}")
    public void existe_una_camilla_tipo_estado_year(String tipo, String estado, Integer year)
            throws CamillaNotFoundException, CamillaAlreadyExistException {
        camilla = new Camilla(tipo, estado, year);
        camillaService.save(camilla);
    }

    @When("solicito la camilla que posee el id {int}")
    public void solicito_la_camilla_que_posee_el_id(Integer idCamilla) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responseCamilla = testRestTemplate.exchange(createURLWithPort("/camillas/findById/" + idCamilla),
                HttpMethod.GET, entity, Camilla.class);
    }

    @Then("obtengo el estado Ok y la camilla con id {int} y tipo {string}")
    public void obtengo_el_estado_ok_y_la_camilla_con_id_y_tipo(Integer idCamilla, String tipo) {

        assertEquals(HttpStatus.OK, responseCamilla.getStatusCode());

        camilla = responseCamilla.getBody();

        assertNotNull(camilla);
        assertEquals(idCamilla, camilla.getId());
        assertEquals(tipo, camilla.getTipo());
    }

    @When("solicito todas las camillas")
    public void solicito_todas_las_camillas() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responseCamillas = testRestTemplate.exchange(createURLWithPort("/camillas/findAll"), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<Camilla>>() {
                });
    }

    @Then("obtengo el estado Ok y {int} camillas")
    public void obtengo_el_estado_ok_y_camillas(Integer nrCamillas) {
        assertEquals(HttpStatus.OK, responseCamillas.getStatusCode());

        List<Camilla> camillas = responseCamillas.getBody();

        assertNotNull(camillas);
        assertEquals(nrCamillas, camillas.size());
    }

    @Given("existe una camilla; id {int}, tipo {string}, estado {string}, year {int}")
    public void existe_una_camilla_id_tipo_estado_year(Integer id, String tipo, String estado, Integer year) throws CamillaNotFoundException, CamillaAlreadyExistException {
        camilla = new Camilla(id,tipo, estado, year);
        camillaService.save(camilla);
    }

    @When("elimino una camilla que posee el id {int}")
    public void elimino_una_camilla_que_posee_el_id(Integer idCamilla) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responseCamilla = testRestTemplate.exchange(createURLWithPort("/camillas/deleteById/" + idCamilla),
                HttpMethod.GET, entity, Camilla.class);
    }

    @Then("obtengo es estado Ok y no lo encuentro con la id {int}")
    public void obtengo_es_estado_ok_y_no_lo_encuentro_con_la_id(Integer idCamilla) {
        assertEquals(HttpStatus.OK, responseCamilla.getStatusCode());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responseCamilla = testRestTemplate.exchange(createURLWithPort("/camillas/deleteById/" + idCamilla),
                HttpMethod.GET, entity, Camilla.class);
        assertEquals(HttpStatus.NOT_FOUND, responseCamilla.getStatusCode());
    }

    @When("edito la camilla que posee el id {int}, cambiando el estado a {string}")
    public void elimino_una_camilla_que_posee_el_id(Integer idCamilla, String newEstado) {

        camilla.setEstado(newEstado);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<Camilla> entity = new HttpEntity<>(camilla, httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responseCamilla = testRestTemplate.exchange(createURLWithPort("/camillas/editCamilla"), HttpMethod.POST, entity,
                Camilla.class);
    }

    @Then("obtengo el estado Ok y la camilla con estado {string}")
    public void obtengo_es_estado_ok_y_no_lo_encuentro_con_la_id(String newEstado) {
        camilla = responseCamilla.getBody();

        assertEquals(HttpStatus.OK, responseCamilla.getStatusCode());
        assertEquals(newEstado, camilla.getEstado());
    }

}
