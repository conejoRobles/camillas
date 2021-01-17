package com.ubb.testing.tdd.Services;

import com.ubb.testing.tdd.Entities.Paciente;
import com.ubb.testing.tdd.Exceptions.PacienteNotFoundException;
import com.ubb.testing.tdd.Repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    Integer ID_PACIENTE_FIND = 1;

    @Test
    public void siSeInvocaFindByIdYExistePacienteDebeRetornarPacienteEncontrado() throws PacienteNotFoundException {
        //Arrange
        Paciente pacienteSearch = new Paciente(1, "19.321.344-3", "Pedro", "Herrera", "Habilitado");
        Paciente pacienteFromService;

        when(pacienteRepository.findById(ID_PACIENTE_FIND)).thenReturn(Optional.of(pacienteSearch));

        //Act
        pacienteFromService = pacienteService.findById(ID_PACIENTE_FIND);

        //Assert
        assertNotNull(pacienteFromService);
        assertEquals(pacienteSearch, pacienteFromService);
    }

    @Test
    public void siSeInvocaFindByIdYNoExistePacienteDebeLanzarPacienteNotFoundException() {
        // Arrange + act
        when(pacienteRepository.findById(ID_PACIENTE_FIND)).thenReturn(Optional.empty());

        //Assert
        assertThrows(PacienteNotFoundException.class, () -> pacienteService.findById(ID_PACIENTE_FIND));

    }

}
