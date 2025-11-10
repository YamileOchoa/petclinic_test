package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @AfterEach
    void cleanup() {
        try {
            visitService.findAll().forEach(v -> {
                try {
                    visitService.delete(v.getId());
                } catch (Exception ignore) {}
            });
        } catch (Exception ignore) {}
    }

    @Test
    void createVisit_shouldPersistAndReturnWithId() {
        Visit v = new Visit();
        v.setVisitDate(LocalDate.now());
        v.setDescription("Visita de control");

        Visit saved = visitService.create(v);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getDescription()).isEqualTo("Visita de control");
    }

    @Test
    void findById_shouldReturnExistingVisit() {
        Visit v = new Visit();
        v.setVisitDate(LocalDate.of(2023, 10, 5));
        v.setDescription("Consulta médica");

        Visit saved = visitService.create(v);
        Visit found = visitService.findById(saved.getId());

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getDescription()).isEqualTo("Consulta médica");
    }

    @Test
    void updateVisit_shouldModifyFields() {
        Visit v = new Visit();
        v.setVisitDate(LocalDate.now());
        v.setDescription("Original");
        Visit saved = visitService.create(v);

        Visit toUpdate = new Visit();
        toUpdate.setVisitDate(LocalDate.now());
        toUpdate.setDescription("Actualizado");

        Visit updated = visitService.update(saved.getId(), toUpdate);

        assertThat(updated).isNotNull();
        assertThat(updated.getDescription()).isEqualTo("Actualizado");
    }

    @Test
    void deleteVisit_shouldRemoveEntity() {
        Visit v = new Visit();
        v.setVisitDate(LocalDate.now());
        v.setDescription("Eliminar");
        Visit saved = visitService.create(v);

        visitService.delete(saved.getId());

        Visit deleted = null;
        try {
            deleted = visitService.findById(saved.getId());
        } catch (Exception ignore) {}

        assertThat(deleted).isNull();
    }
}
