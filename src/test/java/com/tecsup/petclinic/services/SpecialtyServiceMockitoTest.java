package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialtyServiceMockitoTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialtyServiceImpl specialtyService; 
    @Test
    void create_shouldCallRepositorySaveAndReturnSaved() {
        Specialty input = new Specialty(null, "Cardio", "Of A", 8, 16);
        Specialty saved = new Specialty(1, "Cardio", "Of A", 8, 16);

        when(specialtyRepository.save(input)).thenReturn(saved);

        Specialty result = specialtyService.create(input);

        verify(specialtyRepository, times(1)).save(input);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Cardio");
    }

    @Test
    void findById_shouldReturnEntityWhenExists() {
        Specialty s = new Specialty(1, "Dent", "Of B", 9, 17);
        when(specialtyRepository.findById(1)).thenReturn(Optional.of(s));

        Specialty result = specialtyService.findById(1);

        verify(specialtyRepository, times(1)).findById(1);
        assertThat(result.getName()).isEqualTo("Dent");
    }

    @Test
    void update_shouldFetchExistingAndSaveUpdated() {
        Specialty existing = new Specialty(1, "Old", "X", 7, 15);
        Specialty updates = new Specialty(null, "New", "Y", 8, 16);

        when(specialtyRepository.findById(1)).thenReturn(Optional.of(existing));
        when(specialtyRepository.save(any(Specialty.class))).thenAnswer(inv -> inv.getArgument(0));

        Specialty result = specialtyService.update(1, updates);

        ArgumentCaptor<Specialty> captor = ArgumentCaptor.forClass(Specialty.class);
        verify(specialtyRepository).save(captor.capture());
        Specialty savedArg = captor.getValue();

        assertThat(savedArg.getName()).isEqualTo("New");
        assertThat(result.getName()).isEqualTo("New");
    }

    @Test
    void delete_shouldCallRepositoryDeleteById() {
        doNothing().when(specialtyRepository).deleteById(1);
        specialtyService.delete(1);
        verify(specialtyRepository, times(1)).deleteById(1);
    }
}
