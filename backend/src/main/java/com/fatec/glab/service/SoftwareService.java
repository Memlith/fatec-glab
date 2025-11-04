package com.fatec.glab.service;

import com.fatec.glab.exception.IdNotFoundException;
import com.fatec.glab.model.Laboratory;
import com.fatec.glab.model.Software;
import com.fatec.glab.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoftwareService {

    @Autowired
    private SoftwareRepository softwareRepository;

    public List<Software> getAll() {
        return softwareRepository.findAll();
    }

    public Optional<Software> getById(String id) {
        return softwareRepository.findById(id);
    }

    public Software save(Software software) {
        return softwareRepository.save(software);
    }

    public Software update(String id, Software updatedSoftware) {
        Optional<Software> existingSoftware = softwareRepository.findById(id);
        if (existingSoftware.isPresent()) {
            Software software = existingSoftware.get();
            software.setName(updatedSoftware.getName());
            return softwareRepository.save(software);
        } else {
            throw new IdNotFoundException("Software com ID " + id + " não encontrado.");

        }
    }

    public void delete(String id) {
        softwareRepository.deleteById(id);
    }


}
