package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyMapper {

    /**
     * Convert DTO to Entity
     * @param dto
     * @return
     */
    public Specialty mapToEntity(SpecialtyDTO dto) {
        if (dto == null) return null;
        return new Specialty(
                dto.getId(),
                dto.getName(),
                dto.getOffice(),
                dto.getHOpen(),
                dto.getHClose()
        );
    }

    public SpecialtyDTO mapToDto(Specialty entity) {
        if (entity == null) return null;
        return new SpecialtyDTO(
                entity.getId(),
                entity.getName(),
                entity.getOffice(),
                entity.getHOpen(),
                entity.getHClose()
        );
    }

}

