package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
import com.ubb.testing.tdd.Repository.PisoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PisoServiceTest {

    @Mock
    private PisoRepository pisoRepository;

    @InjectMocks
    private PisoServiceImpl pisoServiceImpl;

    List<Piso> pisosFromService;
    ArrayList<Piso> listPisos;
    long ID_PISO_BUSCAR = 1;

    @BeforeEach
    void setup() {
        listPisos = new ArrayList<>();
    }

    @Test
    public void siSeInvocaFindAllPisosYExistenPisosHabilitadosDebeRetornarLaListaDePisos() {
        //Arrange
        listPisos.add(new Piso(1, "Piso 1", "Habilitado", 2));
        listPisos.add(new Piso(2, "Piso 2", "Ocupado", 1));
        listPisos.add(new Piso(3, "Piso 3", "En preparación", 1));
        listPisos.add(new Piso(4, "Piso 4", "Habilitado", 1));
        when(pisoRepository.findAll()).thenReturn(listPisos);

        //Act
        pisosFromService = pisoServiceImpl.findAll();

        //Assert
        assertNotNull(pisosFromService);
        assertEquals(pisosFromService.size(), listPisos.size());
        assertAll("pisosFromRepository",
                () -> assertEquals("Piso 1", pisosFromService.get(0).getNombre()),
                () -> assertEquals("Piso 4", pisosFromService.get(3).getNombre())
        );
    }

    @Test
    public void siSeInvocaFindAllPisosYNoExistenNingunPisoHabilitadoDebeRetornarUnaListaVacia() {
        when(pisoRepository.findAll()).thenReturn(listPisos);
        pisosFromService = pisoServiceImpl.findAll();
        assertNotNull(pisosFromService);
        assertEquals(pisosFromService.size(), listPisos.size());
    }

    @Test
    public void siSeInvocaFindByIdYExisteElPisoDebeRetornarElPisoEncontrado() throws PisoNotFoundException {
        Piso piso = new Piso(1, "Piso 1", "Habilitado", 2);
        Piso pisoFromService;

        when(pisoRepository.findById(ID_PISO_BUSCAR)).thenReturn(Optional.of(piso));

        pisoFromService = pisoServiceImpl.findById(ID_PISO_BUSCAR);

        assertNotNull(pisoFromService);
        assertEquals(piso, pisoFromService);
    }

    @Test
    public void siSeInvocaFindByIdYNoExisteElPisoDebeLanzarNoPisoFoundException() {
        when(pisoRepository.findById(ID_PISO_BUSCAR)).thenReturn(Optional.empty());
        assertThrows(PisoNotFoundException.class, () -> pisoServiceImpl.findById(ID_PISO_BUSCAR));
    }


}
