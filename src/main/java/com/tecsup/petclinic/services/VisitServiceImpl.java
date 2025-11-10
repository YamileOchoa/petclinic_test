package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    @Override
    public Visit create(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public Visit update(Integer id, Visit visit) {
        Visit existing = visitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visit not found"));

        existing.setVisitDate(visit.getVisitDate());
        existing.setDescription(visit.getDescription());

        return visitRepository.save(existing);
    }

    @Override
    public Visit findById(Integer id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visit not found"));
    }

    @Override
    public List<Visit> findAll() {
        return visitRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        visitRepository.deleteById(id);
    }
}
