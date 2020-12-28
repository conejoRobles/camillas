package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Habitacion;
import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Exceptions.HabitacionNotFoundException;
import com.ubb.testing.tdd.Exceptions.PisoNotFoundException;
import com.ubb.testing.tdd.Repository.HabitacionRepository;
import org.junit.jupiter.api.BeforeEach;
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

    List<Habitacion> habitacionesFromService;
    List<Habitacion> habitacionesList;
    private Long ID_HABITACION = 2L;

    @BeforeEach
    void setup() {
        habitacionesList = new ArrayList<>();
    }

    @Test
    public void siSeInvocaFindAllHabitacionesYExistenHabitacionesDisponiblesDebeRetornarUnListaDeHabitaciones() {
        //Arrange
        habitacionesList.add(new Habitacion(1, "Cardiologia", "Disponible", 2));
        habitacionesList.add(new Habitacion(2, "Pediatria", "Disponible", 3));
        habitacionesList.add(new Habitacion(3, "UCI", "Disponible", 6));
        habitacionesList.add(new Habitacion(4, "Urgencias", "Disponible", 5));
        when(habitacionRepository.findAll()).thenReturn(habitacionesList);

        //Act
        habitacionesFromService = habitacionService.findAll();

        //Assert
        assertNotNull(habitacionesFromService);
        assertEquals(habitacionesFromService.size(), habitacionesList.size());
        assertAll("Verificando que dos elementos de la lista service posean los mismos datos de la lista en arrange",
                () -> assertEquals("Cardiologia", habitacionesFromService.get(0).getEspecialidad()),
                () -> assertEquals("Urgencias", habitacionesFromService.get(3).getEspecialidad()));
    }

    @Test
    public void siSeInvocaFindAllHabitacionesYNoExistenDatosDebeRetornarListaVacia() {
        // Arrange
        when(habitacionRepository.findAll()).thenReturn(habitacionesList);

        //Act
        habitacionesFromService = habitacionService.findAll();

        //Assert
        assertNotNull(habitacionesFromService);
        assertEquals(habitacionesFromService.size(), 0);
    }

    @Test
    public void siSeInvocaFindByIdYNoExisteHabitacionDebeLanzarHabitacionNotFoundException() {
        //Arrange + Act
        when(habitacionRepository.findById(ID_HABITACION)).thenReturn(Optional.empty());

        //Assert
        assertThrows(HabitacionNotFoundException.class, () -> habitacionService.findById(ID_HABITACION));
    }

    @Test
    public void siSeInvocaFindBIdYExisteLaHabitacionDebeRetornarLaHabitacionEncontrada() throws HabitacionNotFoundException {
        //Arrange
        Habitacion habitacion  = new Habitacion(2, "Cardiologia", "Disponible", 2);
        Habitacion habitacionFromService;
        when(habitacionRepository.findById(ID_HABITACION)).thenReturn(Optional.of(habitacion));

        //Act
        habitacionFromService = habitacionService.findById(ID_HABITACION);

        //Assert
        assertNotNull(habitacionFromService);
        assertEquals(habitacion, habitacionFromService);
    }

    @Test
    public void siSeInvocaDeleteByIdYExisteLaHabitacionDebeEliminarlo() throws HabitacionNotFoundException {
        long id = 2;
        Habitacion habitacion  = new Habitacion(2, "Cardiologia", "Disponible", 2);
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
