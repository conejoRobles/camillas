package com.ubb.testing.tdd.Services;
import com.ubb.testing.tdd.Entities.Piso;
import com.ubb.testing.tdd.Repository.PisoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PisoServiceTest {

    @Mock
    private PisoRepository pisoRepository;

    @InjectMocks
    private PisoServiceImpl pisoServiceImpl;

    @Test
    public void siSeInvocaFindAllPisosYExistenPisosHabilitadosDebeRetornarLaListaDePisos(){
        //Arrange
        List<Piso> pisosFromRepository;
        ArrayList<Piso> listPisos = new ArrayList<>();
        listPisos.add(new Piso("Piso 1", "Habilitado", 2));
        listPisos.add(new Piso("Piso 2", "Ocupado", 1));
        listPisos.add(new Piso("Piso 3", "En sanitización", 1));
        listPisos.add(new Piso("Piso 4", "Habilitado", 1));

        when(pisoRepository.findAll()).thenReturn(listPisos);

        pisosFromRepository = pisoServiceImpl.findAll();

        assertNotNull(pisosFromRepository);
        assertEquals(pisosFromRepository.size(), listPisos.size());
        assertAll("pisosFromRepository",
                () -> assertEquals("Piso 1", pisosFromRepository.get(0).getNombre()),
                () -> assertEquals("Piso 4", pisosFromRepository.get(3).getNombre())
        );
    }

}
