package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

	@Autowired
	private SpecialtyService specialtyService;

	/**
	 * Test para buscar una especialidad por ID
	 */
	@Test
	public void testFindSpecialtyById() {

		String NAME_EXPECTED = "radiology";

		Integer ID = 1;

		SpecialtyDTO specialty = null;

		try {
			specialty = this.specialtyService.findById(ID);
		} catch (SpecialtyNotFoundException e) {
			fail(e.getMessage());
		}
		log.info("" + specialty);

		assertEquals(NAME_EXPECTED, specialty.getName());
	}

	/**
	 * Test para crear una nueva especialidad
	 */
	@Test
	public void testCreateSpecialty() {

		String SPECIALTY_NAME = "cardiology";
		String OFFICE = "Central";
		Integer H_OPEN = 7;
		Integer H_CLOSE = 17;

		SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
				.name(SPECIALTY_NAME)
				.office(OFFICE)
				.hOpen(H_OPEN)
				.hClose(H_CLOSE)
				.build();

		SpecialtyDTO newSpecialtyDTO = this.specialtyService.create(specialtyDTO);

		log.info("SPECIALTY CREATED :" + newSpecialtyDTO.toString());

		assertNotNull(newSpecialtyDTO.getId());
		assertEquals(SPECIALTY_NAME, newSpecialtyDTO.getName());
		assertEquals(OFFICE, newSpecialtyDTO.getOffice());
		assertEquals(H_OPEN, newSpecialtyDTO.getHOpen());
		assertEquals(H_CLOSE, newSpecialtyDTO.getHClose());
	}

	/**
	 * Test para actualizar una especialidad
	 */
	@Test
	public void testUpdateSpecialty() {

		String SPECIALTY_NAME = "neurology";
		String OFFICE = "North";
		Integer H_OPEN = 8;
		Integer H_CLOSE = 18;

		String UP_SPECIALTY_NAME = "neurology_updated";
		String UP_OFFICE = "South";
		Integer UP_H_OPEN = 9;
		Integer UP_H_CLOSE = 19;

		SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
				.name(SPECIALTY_NAME)
				.office(OFFICE)
				.hOpen(H_OPEN)
				.hClose(H_CLOSE)
				.build();

		// ------------ Create ---------------

		log.info(">" + specialtyDTO);
		SpecialtyDTO specialtyDTOCreated = this.specialtyService.create(specialtyDTO);
		log.info(">>" + specialtyDTOCreated);

		// ------------ Update ---------------

		// Prepare data for update
		specialtyDTOCreated.setName(UP_SPECIALTY_NAME);
		specialtyDTOCreated.setOffice(UP_OFFICE);
		specialtyDTOCreated.setHOpen(UP_H_OPEN);
		specialtyDTOCreated.setHClose(UP_H_CLOSE);

		// Execute update
		SpecialtyDTO updatedSpecialtyDTO = this.specialtyService.update(specialtyDTOCreated);
		log.info(">>>>" + updatedSpecialtyDTO);

		//            EXPECTED        ACTUAL
		assertEquals(UP_SPECIALTY_NAME, updatedSpecialtyDTO.getName());
		assertEquals(UP_OFFICE, updatedSpecialtyDTO.getOffice());
		assertEquals(UP_H_OPEN, updatedSpecialtyDTO.getHOpen());
		assertEquals(UP_H_CLOSE, updatedSpecialtyDTO.getHClose());
	}

	/**
	 * Test para eliminar una especialidad
	 */
	@Test
	public void testDeleteSpecialty() {

		String SPECIALTY_NAME = "dermatology";
		String OFFICE = "East";
		Integer H_OPEN = 6;
		Integer H_CLOSE = 16;

		// ------------ Create ---------------

		SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
				.name(SPECIALTY_NAME)
				.office(OFFICE)
				.hOpen(H_OPEN)
				.hClose(H_CLOSE)
				.build();

		SpecialtyDTO newSpecialtyDTO = this.specialtyService.create(specialtyDTO);
		log.info("" + newSpecialtyDTO);

		// ------------ Delete ---------------

		try {
			this.specialtyService.delete(newSpecialtyDTO.getId());
		} catch (SpecialtyNotFoundException e) {
			fail(e.getMessage());
		}

		// ------------ Validation ---------------

		try {
			this.specialtyService.findById(newSpecialtyDTO.getId());
			assertTrue(false);
		} catch (SpecialtyNotFoundException e) {
			assertTrue(true);
		}
	}
}
