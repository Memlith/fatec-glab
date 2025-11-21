package com.fatec.glab.controller;

import com.fatec.glab.dto.software.SoftwareRequestDTO;
import com.fatec.glab.dto.software.SoftwareRequestUpdateDTO;
import com.fatec.glab.dto.software.SoftwareResponseDTO;
import com.fatec.glab.mapper.SoftwareMapper;
import com.fatec.glab.model.Software;
import com.fatec.glab.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/softwares")
public class SoftwareController {

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private SoftwareMapper softwareMapper;

    @GetMapping
    public ResponseEntity<List<SoftwareResponseDTO>> getAll() {
        List<Software> softwares = softwareService.getAll();
        List<SoftwareResponseDTO> responseDTOs = softwareMapper.toDTO(softwares);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoftwareResponseDTO> getById(@PathVariable String id) {
        Software software = softwareService.getById(id);
        SoftwareResponseDTO responseDTO = softwareMapper.toDTO(software);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<SoftwareResponseDTO> create(@RequestBody SoftwareRequestDTO softwareRequestDTO) {
        Software savedSoftware = softwareService.save(softwareRequestDTO);
        SoftwareResponseDTO responseDTO = softwareMapper.toDTO(savedSoftware);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<SoftwareResponseDTO> update(@PathVariable String id, @RequestBody SoftwareRequestUpdateDTO softwareRequestDTO) {
        Software updatedSoftware = softwareService.update(id, softwareRequestDTO);
        SoftwareResponseDTO responseDTO = softwareMapper.toDTO(updatedSoftware);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        softwareService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
