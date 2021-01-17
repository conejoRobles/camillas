package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Historial;
import com.ubb.testing.tdd.Exceptions.HistorialNotFoundException;
import com.ubb.testing.tdd.Repository.HistorialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class HistorialServiceTest {

    @Mock
    private HistorialRepository historialRepository;

    @InjectMocks
    private HistorialServiceImpl historialService;

    public int ID_HISTORIAL_BUSCAR = 1;

    @Test
    public void siSeInvocaFindByIdYExisteHistorialDebeRetornarHistorialEncontrado() throws HistorialNotFoundException, ParseException {
        //Arrange
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");
        Date fecIngreso = objSDF.parse("20-01-2021");
        Date fecSalida = objSDF.parse("25-01-2021");

        Historial historialToFind = new Historial(ID_HISTORIAL_BUSCAR, fecIngreso, fecSalida);
        Historial historialFromService;
        when(historialRepository.findById(ID_HISTORIAL_BUSCAR)).thenReturn(Optional.of(historialToFind));

        //Act
        historialFromService = historialService.findById(ID_HISTORIAL_BUSCAR);

        //Assert
        assertNotNull(historialFromService);
        assertEquals(historialToFind, historialFromService);
    }

    @Test
    public void siSeInvocaFindByIdYNoExisteHistorialDebeLanzarHistorialNotFoundException() {
        // Arrange + Act
        when(historialRepository.findById(ID_HISTORIAL_BUSCAR)).thenReturn(Optional.empty());

        //Assert
        assertThrows(HistorialNotFoundException.class, () -> historialService.findById(ID_HISTORIAL_BUSCAR));
    }
}
