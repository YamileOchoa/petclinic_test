package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    @AfterEach
    void cleanup() {
        try {
            specialtyService.findAll().forEach(s -> {
                try {
                    specialtyService.delete(s.getId());
                } catch (Exception ignore) {}
            });
        } catch (Exception ignore) {}
    }

    @Test
    void createSpecialty_shouldPersistAndReturnWithId() {
        Specialty s = new Specialty();
        s.setName("Cardiología");
        s.setOffice("Oficina A");
        s.setHOpen(8);
        s.setHClose(16);

        Specialty saved = specialtyService.create(s);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Cardiología");
        assertThat(saved.getOffice()).isEqualTo("Oficina A");
    }

    @Test
    void findById_shouldReturnExistingSpecialty() {
        Specialty s = new Specialty();
        s.setName("Dermatología");
        Specialty saved = specialtyService.create(s);

        Specialty found = specialtyService.findById(saved.getId());

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getName()).isEqualTo("Dermatología");
    }

    @Test
    void updateSpecialty_shouldModifyFields() {
        Specialty s = new Specialty();
        s.setName("Antiguo");
        s.setOffice("X");
        Specialty saved = specialtyService.create(s);

        Specialty toUpdate = new Specialty();
        toUpdate.setName("Actualizado");
        toUpdate.setOffice("Oficina B");
        toUpdate.setHOpen(9);
        toUpdate.setHClose(17);

        Specialty updated = specialtyService.update(saved.getId(), toUpdate);

        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo("Actualizado");
        assertThat(updated.getOffice()).isEqualTo("Oficina B");
        assertThat(updated.getHOpen()).isEqualTo(9);
        assertThat(updated.getHClose()).isEqualTo(17);
    }

    @Test
    void deleteSpecialty_shouldRemoveEntity() {
        Specialty s = new Specialty();
        s.setName("Temporal");
        Specialty saved = specialtyService.create(s);

        specialtyService.delete(saved.getId());

        Specialty deleted = null;
        try {
            deleted = specialtyService.findById(saved.getId());
        } catch (Exception ignore) { }

        assertThat(deleted).isNull();
    }
}
