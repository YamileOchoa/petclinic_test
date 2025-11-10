package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceMockitoTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitServiceImpl visitService;

    @Test
    void create_shouldSaveVisitAndReturnSaved() {
        Visit input = new Visit(null, LocalDate.now(), "Control");
        Visit saved = new Visit(1, LocalDate.now(), "Control");

        when(visitRepository.save(input)).thenReturn(saved);

        Visit result = visitService.create(input);

        verify(visitRepository, times(1)).save(input);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getDescription()).isEqualTo("Control");
    }

    @Test
    void findById_shouldReturnVisitWhenExists() {
        Visit v = new Visit(1, LocalDate.now(), "Consulta");
        when(visitRepository.findById(1)).thenReturn(Optional.of(v));

        Visit result = visitService.findById(1);

        verify(visitRepository, times(1)).findById(1);
        assertThat(result.getDescription()).isEqualTo("Consulta");
    }

    @Test
    void update_shouldModifyAndSaveVisit() {
        Visit existing = new Visit(1, LocalDate.now(), "Antiguo");
        Visit updates = new Visit(null, LocalDate.now(), "Actualizado");

        when(visitRepository.findById(1)).thenReturn(Optional.of(existing));
        when(visitRepository.save(any(Visit.class))).thenAnswer(inv -> inv.getArgument(0));

        Visit result = visitService.update(1, updates);

        verify(visitRepository, times(1)).save(existing);
        assertThat(result.getDescription()).isEqualTo("Actualizado");
    }

    @Test
    void delete_shouldCallRepositoryDeleteById() {
        doNothing().when(visitRepository).deleteById(1);
        visitService.delete(1);
        verify(visitRepository, times(1)).deleteById(1);
    }
}
