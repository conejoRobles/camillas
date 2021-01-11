package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;
import com.ubb.testing.tdd.Repository.CamillaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CamillaServiceTest {

    @Mock
    private CamillaRepository camillaRepository;

    @InjectMocks
    private CamillaServiceImpl camillaServiceImpl;

    public int ID_CAMILLA_BUSCAR = 1;

    @Test
    public void siSeInvocaFindByIdYExisteCamillaDebeRetornarLaCamillaEncontrada() throws CamillaNotFoundException {
        //Arrange
        Camilla camillaToFind = new Camilla(ID_CAMILLA_BUSCAR, "Camilla Plegable", "Libre", 2020);
        Camilla camillaFromService;
        when(camillaRepository.findById(ID_CAMILLA_BUSCAR)).thenReturn(Optional.of(camillaToFind));

        //Act
        camillaFromService = camillaServiceImpl.findById(ID_CAMILLA_BUSCAR);

        //Assert
        assertNotNull(camillaFromService);
        assertEquals(camillaToFind, camillaFromService);
    }

    @Test
    public void siSeInvocaFindByIdYNoExisteCamillaDebeLanzarCamillaNotFoundException() {
        // Arrange + Act
        when(camillaRepository.findById(ID_CAMILLA_BUSCAR)).thenReturn(Optional.empty());

        //Assert
        assertThrows(CamillaNotFoundException.class, () -> camillaServiceImpl.findById(ID_CAMILLA_BUSCAR));
    }
}
