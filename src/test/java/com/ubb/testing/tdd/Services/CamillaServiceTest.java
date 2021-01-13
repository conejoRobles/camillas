package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Camilla;
import com.ubb.testing.tdd.Entities.Habitacion;
import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.CamillaNotFoundException;
import com.ubb.testing.tdd.Exceptions.HabitacionNotFoundException;
import com.ubb.testing.tdd.Repository.CamillaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNull;
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
        // Arrange
        Camilla camillaToFind = new Camilla(ID_CAMILLA_BUSCAR, "Camilla Plegable", "Libre", 2020);
        Camilla camillaFromService;
        when(camillaRepository.findById(ID_CAMILLA_BUSCAR)).thenReturn(Optional.of(camillaToFind));

        // Act
        camillaFromService = camillaServiceImpl.findById(ID_CAMILLA_BUSCAR);

        // Assert
        assertNotNull(camillaFromService);
        assertEquals(camillaToFind, camillaFromService);
    }

    @Test
    public void siSeInvocaFindByIdYNoExisteCamillaDebeLanzarCamillaNotFoundException() {
        // Arrange + Act
        when(camillaRepository.findById(ID_CAMILLA_BUSCAR)).thenReturn(Optional.empty());

        // Assert
        assertThrows(CamillaNotFoundException.class, () -> camillaServiceImpl.findById(ID_CAMILLA_BUSCAR));
    }

    @Test
    public void siSeInvocaFindAllYExistenCamillasDebeRetornarLaListaDeCamillas() {
        // Arrange
        List<Camilla> camillaList = new ArrayList<>();
        camillaList.add(new Camilla(1, "Camilla Plegable XL", "Libre", 2020));
        camillaList.add(new Camilla(2, "Camilla Plegable L", "Ocupada", 2018));
        camillaList.add(new Camilla(3, "Camilla Plegable M", "En mantencion", 2020));
        when(camillaRepository.findAll()).thenReturn(camillaList);

        // Act
        List<Camilla> listCamillasFromService = camillaServiceImpl.findAll();

        // Assert
        assertNotNull(listCamillasFromService);
        assertEquals(listCamillasFromService.size(), camillaList.size());
        assertAll("Verificando objetos",
                () -> assertEquals("Camilla Plegable XL", listCamillasFromService.get(0).getTipo()),
                () -> assertEquals("Camilla Plegable M", listCamillasFromService.get(2).getTipo()));
    }

    @Test
    public void siSeInvocaDeleteByIdYExisteLaCamillaDebeEliminarlo()
            throws HabitacionNotFoundException, CamillaNotFoundException {
        Camilla camilla = new Camilla(1, "Camilla Plegable", "Libre", 2020);
        when(camillaRepository.findById(ID_CAMILLA_BUSCAR)).thenReturn(Optional.of(camilla));
        camillaServiceImpl.deleteById(1);
        verify(camillaRepository, times(1)).deleteById(1);
    }

    @Test
    public void siSeInvocaDeleteByIdYNoExisteLaCamillaDebeLanzarNoPisoFoundException() {
        when(camillaRepository.findById(ID_CAMILLA_BUSCAR)).thenReturn(Optional.empty());
        assertThrows(CamillaNotFoundException.class, () -> camillaServiceImpl.deleteById(ID_CAMILLA_BUSCAR));
    }

    @Test
    public void siSeEditaUnaCamillaExitosamenteRetornaLaCamillaConLosNuevosValores() {

        // Arrange
        Camilla camilla = new Camilla(ID_CAMILLA_BUSCAR, "Camilla Plegable", "Libre", 2020);
        String newEstado = "Ocupada";

        when(camillaRepository.findById(ID_CAMILLA_BUSCAR)).thenReturn(Optional.of(camilla));
        when(camillaRepository.save(camilla)).thenReturn(camilla);

        camilla.setEstado(newEstado);

        assertEquals(newEstado, camillaServiceImpl.edit(camilla).getEstado());

    }

    @Test
    public void siSeEditaUnaCamillaYNoExisteRetornaNull() {

        // Arrange
        Camilla camilla = new Camilla(ID_CAMILLA_BUSCAR, "Camilla Plegable", "Libre", 2020);
        String newEstado = "Ocupada";

        when(camillaRepository.findById(ID_CAMILLA_BUSCAR)).thenReturn(null);

        camilla.setEstado(newEstado);

        assertNull(camillaServiceImpl.edit(camilla));

    }

}
