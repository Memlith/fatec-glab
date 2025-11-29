package com.fatec.glab.service;

import java.util.List;

import com.fatec.glab.dto.software.SoftwareRequestDTO;
import com.fatec.glab.dto.software.SoftwareRequestUpdateDTO;
import com.fatec.glab.mapper.SoftwareMapper;
import com.fatec.glab.model.Software;
import com.fatec.glab.repository.SoftwareRepository;
import com.fatec.glab.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SoftwareService {

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private SoftwareMapper softwareMapper;

    @Transactional
    public Software save(SoftwareRequestDTO softwareRequestDTO) {
        Software software = softwareMapper.toEntity(softwareRequestDTO);
        return softwareRepository.save(software);
    }

    public Software getById(String id) {
        return softwareRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Software com ID " + id + " não encontrado."));
    }

    public List<Software> getAll() {
        return softwareRepository.findAll();
    }

    @Transactional
    public Software update(String id, SoftwareRequestUpdateDTO softwareRequestUpdateDTO) {
        Software existingSoftware = softwareRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Software com ID " + id + " não encontrado."));

        softwareMapper.updateFromDTO(softwareRequestUpdateDTO, existingSoftware);

        return softwareRepository.save(existingSoftware);
    }

    @Transactional
    public void delete(String id) {
        softwareRepository.deleteById(id);
    }
}
