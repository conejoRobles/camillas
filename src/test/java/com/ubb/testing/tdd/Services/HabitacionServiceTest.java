package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Habitacion;
import com.ubb.testing.tdd.Exceptions.HabitacionAlreadyExistsException;
import com.ubb.testing.tdd.Exceptions.HabitacionNotFoundException;
import com.ubb.testing.tdd.Repository.HabitacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HabitacionServiceTest {

    @Mock
    private HabitacionRepository habitacionRepository;

    @InjectMocks
    private HabitacionServiceImpl habitacionService;

    List<Habitacion> listHabitacionesFromService;
    private Long ID_HABITACION = 2L;


    @Test
    public void siSeInvocaFindAllHabitacionesYExistenHabitacionesDisponiblesDebeRetornarUnListaDeHabitaciones() {
        // Arrange
        List<Habitacion> habitacionesList = new ArrayList<>();
        habitacionesList.add(new Habitacion(1, "Cardiologia", "Disponible", 2));
        habitacionesList.add(new Habitacion(2, "Pediatria", "Disponible", 3));
        habitacionesList.add(new Habitacion(3, "UCI", "Disponible", 6));
        habitacionesList.add(new Habitacion(4, "Urgencias", "Disponible", 5));
        when(habitacionRepository.findAll()).thenReturn(habitacionesList);

        // Act
        listHabitacionesFromService = habitacionService.findAll();

        // Assert
        assertNotNull(listHabitacionesFromService);
        assertEquals(listHabitacionesFromService.size(), habitacionesList.size());
        assertAll("Verificando que dos elementos de la lista service posean los mismos datos de la lista en arrange",
                () -> assertEquals("Cardiologia", listHabitacionesFromService.get(0).getEspecialidad()),
                () -> assertEquals("Urgencias", listHabitacionesFromService.get(3).getEspecialidad()));
    }

    @Test
    public void siSeInvocaFindAllHabitacionesYNoExistenDatosDebeRetornarListaVacia() {
        List<Habitacion> habitacionesList = new ArrayList<>();
        // Arrange
        when(habitacionRepository.findAll()).thenReturn(habitacionesList);

        // Act
        listHabitacionesFromService = habitacionService.findAll();

        // Assert
        assertNotNull(listHabitacionesFromService);
        assertEquals(listHabitacionesFromService.size(), 0);
    }

    @Test
    public void siSeInvocaFindByIdYNoExisteHabitacionDebeLanzarHabitacionNotFoundException() {
        // Arrange + Act
        when(habitacionRepository.findById(ID_HABITACION)).thenReturn(Optional.empty());

        // Assert
        assertThrows(HabitacionNotFoundException.class, () -> habitacionService.findById(ID_HABITACION));
    }

    @Test
    public void siSeInvocaFindBIdYExisteLaHabitacionDebeRetornarLaHabitacionEncontrada()
            throws HabitacionNotFoundException {
        // Arrange
        Habitacion habitacion = new Habitacion(2, "Cardiologia", "Disponible", 2);
        Habitacion habitacionFromService;
        when(habitacionRepository.findById(ID_HABITACION)).thenReturn(Optional.of(habitacion));

        // Act
        habitacionFromService = habitacionService.findById(ID_HABITACION);

        // Assert
        assertNotNull(habitacionFromService);
        assertEquals(habitacion, habitacionFromService);
    }

    @Test
    public void siSeInvocaSaveAgregaLaHabitacionYRetornaLaHabitacion() throws HabitacionAlreadyExistsException {
        // Arrange

        Habitacion habitacion = new Habitacion(30, "Neurologia", "Ocupado", 1);
        Habitacion obtenida;
        when(habitacionRepository.save(habitacion)).thenReturn(habitacion);

        // Act
        obtenida = habitacionRepository.save(habitacion);

        assertEquals(habitacion, obtenida);

    }

    @Test
    public void siSeEditaUnaHabitacionExitosamenteRetornaLaHabitacionConLosNuevosValores() {

        Habitacion habitacion = new Habitacion(2, "Cardiologia", "Disponible", 2);

        String nuevoNombre = "Urgencias";
        int nuevoNumeroDeCamas = 20;
        when(habitacionRepository.save(habitacion)).thenReturn(habitacion);

        habitacion.setEspecialidad(nuevoNombre);
        habitacion.setNroCamasMax(nuevoNumeroDeCamas);

        assertAll("Verificando todos los cambios del piso",
                () -> assertEquals(nuevoNombre, habitacionService.edit(habitacion).getEspecialidad()),
                () -> assertEquals(nuevoNumeroDeCamas, habitacionService.edit(habitacion).getNroCamasMax()));
    }

    @Test
    public void siSeEditaUnPisoYNoExisteRetornaNull() {

        Habitacion habitacion = new Habitacion(2, "Cardiologia", "Disponible", 2);

        String nuevoNombre = "Urgencias";
        int nuevoNumeroDeCamas = 20;

        when(habitacionRepository.findById(habitacion.getId())).thenReturn(null);

        habitacion.setEspecialidad(nuevoNombre);
        habitacion.setNroCamasMax(nuevoNumeroDeCamas);

        assertNull(habitacionService.edit(habitacion));
    }

    @Test
    public void siSeInvocaDeleteByIdYExisteLaHabitacionDebeEliminarlo() throws HabitacionNotFoundException {
        long id = 2;
        Habitacion habitacion = new Habitacion(2, "Cardiologia", "Disponible", 2);
        when(habitacionRepository.findById(ID_HABITACION)).thenReturn(Optional.of(habitacion));
        habitacionService.deleteById(id);
        verify(habitacionRepository, times(1)).deleteById(id);
    }

    @Test
    public void siSeInvocaDeleteByIdYNoExisteLaHabitacionDebeLanzarNoPisoFoundException() {
        when(habitacionRepository.findById(ID_HABITACION)).thenReturn(Optional.empty());
        assertThrows(HabitacionNotFoundException.class, () -> habitacionService.deleteById(ID_HABITACION));
    }

}
