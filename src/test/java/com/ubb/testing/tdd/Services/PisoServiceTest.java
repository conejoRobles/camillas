package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.PisoAlreadyExistsException;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
import com.ubb.testing.tdd.Repository.PisoRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PisoServiceTest {

    @Mock
    private PisoRepository pisoRepository;

    @InjectMocks
    private PisoServiceImpl pisoServiceImpl;

    List<Piso> pisosFromService;
    ArrayList<Piso> listPisos;
    Integer ID_PISO_BUSCAR = 1;

    @BeforeEach
    void setup() {
        listPisos = new ArrayList<>();
    }

    @Test
    public void siSeInvocaFindAllPisosYExistenPisosHabilitadosDebeRetornarLaListaDePisos() {
        // Arrange
        listPisos.add(new Piso(1, "Piso 1", "Habilitado", 2));
        listPisos.add(new Piso(2, "Piso 2", "Ocupado", 1));
        listPisos.add(new Piso(3, "Piso 3", "En preparación", 1));
        listPisos.add(new Piso(4, "Piso 4", "Habilitado", 1));
        when(pisoRepository.findAll()).thenReturn(listPisos);

        // Act
        pisosFromService = pisoServiceImpl.findAll();

        // Assert
        assertNotNull(pisosFromService);
        assertEquals(pisosFromService.size(), listPisos.size());
        assertAll("pisosFromRepository",
                () -> assertEquals("Piso 1", pisosFromService.get(0).getNombre()),
                () -> assertEquals("Piso 4", pisosFromService.get(3).getNombre()));
    }

    @Test
    public void siSeInvocaFindByIdYNoExisteElPisoDebeLanzarNoPisoFoundException() {
        // Arrange + Act
        when(pisoRepository.findById(ID_PISO_BUSCAR)).thenReturn(Optional.empty());
        // Assert
        assertThrows(PisoNotFoundException.class, () -> pisoServiceImpl.findById(ID_PISO_BUSCAR));
    }

    @Test
    public void siSeInvocaFindAllPisosYNoExistenNingunPisoHabilitadoDebeRetornarUnaListaVacia() {
        // Arrange
        when(pisoRepository.findAll()).thenReturn(listPisos);

        // Act
        pisosFromService = pisoServiceImpl.findAll();

        // Assert
        assertNotNull(pisosFromService);
        assertEquals(pisosFromService.size(), 0);
    }

    @Test
    public void siSeInvocaFindByIdYExisteElPisoDebeRetornarElPisoEncontrado() throws PisoNotFoundException {
        // Arrange
        Piso piso = new Piso(1, "Piso 1", "Habilitado", 2);
        Piso pisoFromService;

        when(pisoRepository.findById(ID_PISO_BUSCAR)).thenReturn(Optional.of(piso));

        // Act
        pisoFromService = pisoServiceImpl.findById(ID_PISO_BUSCAR);

        // Assert
        assertNotNull(pisoFromService);
        assertEquals(piso, pisoFromService);
    }

    @Test
    public void siSeEditaUnPisoExitosamenteRetornaElPisoConLosNuevosValores() {

        Piso piso1 = new Piso(1, "Piso 1", "Habilitado", 2);
        String nuevoNombre = "Primer Piso";
        String nuevoEstado = "Deshabilitado";
        int nuevoNumeroDeHabitaciones = 20;
        when(pisoRepository.save(piso1)).thenReturn(piso1);

        piso1.setNombre(nuevoNombre);
        piso1.setEstado(nuevoEstado);
        piso1.setNroHabitaciones(nuevoNumeroDeHabitaciones);

        assertAll("Verificando todos los cambios del piso",
                () -> assertEquals(nuevoNombre, pisoServiceImpl.edit(piso1).getNombre()),
                () -> assertEquals(nuevoEstado, pisoServiceImpl.edit(piso1).getEstado()),
                () -> assertEquals(nuevoNumeroDeHabitaciones, pisoServiceImpl.edit(piso1).getNroHabitaciones()));
    }

    @Test
    public void siSeEditaUnPisoYNoExisteRetornaNull() {

        Piso piso = new Piso();
        String nuevoNombre = "Primer Piso";
        String nuevoEstado = "Deshabilitado";
        int nuevoNumeroDeHabitaciones = 20;
        when(pisoRepository.findById(piso.getId())).thenReturn(null);

        piso.setNombre(nuevoNombre);
        piso.setEstado(nuevoEstado);
        piso.setNroHabitaciones(nuevoNumeroDeHabitaciones);

        assertNull(pisoServiceImpl.edit(piso));
    }

    @Test
    public void siSeInvocaDeleteByIdYExisteElPisoDebeEliminarlo() throws PisoNotFoundException {
        Piso piso = new Piso(1, "Piso 1", "Habilitado", 2);
        when(pisoRepository.findById(ID_PISO_BUSCAR)).thenReturn(Optional.of(piso));
        pisoServiceImpl.deleteById(1);
        verify(pisoRepository, times(1)).deleteById(1);
    }

    @Test
    public void siSeInvocaDeleteByIdYNoExisteElPisoDebeLanzarNoPisoFoundException() {
        when(pisoRepository.findById(ID_PISO_BUSCAR)).thenReturn(Optional.empty());
        assertThrows(PisoNotFoundException.class, () -> pisoServiceImpl.deleteById(ID_PISO_BUSCAR));
    }

    @Test
    public void siSeInvocaSavePisoYAgregoUnPisoDebeRetornarElPisoAgregado() throws PisoNotFoundException, PisoAlreadyExistsException {

    	List<Piso> pisos = new ArrayList<Piso>();
    	
    	
        Piso piso1 = new Piso(1, "Piso 1", "Habilitado", 15);
        Piso piso2 = new Piso(2, "Piso 2", "Habilitado", 29);
        Piso piso3 = new Piso(3, "Piso 3", "Habilitado", 7);
        Piso piso4 = new Piso(4, "Piso 4", "Habilitado", 5);

        pisos.add(piso1);
        pisos.add(piso2);
        pisos.add(piso3);
    

        when(pisoRepository.findAll()).thenReturn(pisos);
        pisoRepository.save(piso4);

        verify(pisoRepository, times(1)).save(piso4);

    }

    @Test
    public void siSeInvocaSavePisoYAgregoUnPisoDebeRetornarUnPisoErroneo() throws PisoNotFoundException, PisoAlreadyExistsException {

        Piso piso1 = new Piso(1, "Piso 1", "Habilitado", 15);
        Piso piso2 = new Piso(1, "Piso 2", "Ocupado", 20);
        when(pisoRepository.save(piso2)).thenReturn(piso2);

        assertNotEquals(piso1, pisoRepository.save(piso2));

    }


}
