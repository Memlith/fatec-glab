package com.fatec.glab.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.glab.exception.IdNotFoundException;
import com.fatec.glab.model.Laboratory;
import com.fatec.glab.repository.LaboratoryRepository;

@Service
public class LaboratoryService {

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    public List<Laboratory> getAll() {
        return laboratoryRepository.findAll();
    }

    public Optional<Laboratory> getById(String id) {
        return laboratoryRepository.findById(id);
    }

    public Laboratory save(Laboratory laboratory) {
        return laboratoryRepository.save(laboratory);
    }

    public Laboratory update(String id, Laboratory updatedLaboratory) {
        Optional<Laboratory> existingLaboratory = laboratoryRepository.findById(id);
        if (existingLaboratory.isPresent()) {
            Laboratory laboratory = existingLaboratory.get();
            laboratory.setName(updatedLaboratory.getName());
            laboratory.setLocation(updatedLaboratory.getLocation());
            laboratory.setCapacity(updatedLaboratory.getCapacity());
            return laboratoryRepository.save(laboratory);
        } else {
            throw new IdNotFoundException("Laboratory com ID " + id + " não encontrado.");

        }
    }

    public void delete(String id) {
        laboratoryRepository.deleteById(id);
    }

}
