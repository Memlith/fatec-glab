package com.fatec.glab.controller;

import com.fatec.glab.dto.equipment.EquipmentRequestDTO;
import com.fatec.glab.dto.equipment.EquipmentRequestUpdateDTO;
import com.fatec.glab.dto.equipment.EquipmentResponseDTO;
import com.fatec.glab.mapper.EquipmentMapper;
import com.fatec.glab.model.Equipment;
import com.fatec.glab.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @GetMapping
    public ResponseEntity<List<EquipmentResponseDTO>> getAll() {
        List<Equipment> equipments = equipmentService.getAll();
        List<EquipmentResponseDTO> responseDTOs = equipmentMapper.toDTO(equipments);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> getById(@PathVariable String id) {
        Equipment equipment = equipmentService.getById(id);
        EquipmentResponseDTO responseDTO = equipmentMapper.toDTO(equipment);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EquipmentResponseDTO> create(@RequestBody EquipmentRequestDTO equipmentRequestDTO) {
        Equipment savedEquipment = equipmentService.save(equipmentRequestDTO);
        EquipmentResponseDTO responseDTO = equipmentMapper.toDTO(savedEquipment);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<EquipmentResponseDTO> update(@PathVariable String id, @RequestBody EquipmentRequestUpdateDTO equipmentRequestDTO) {
        Equipment updatedEquipment = equipmentService.update(id, equipmentRequestDTO);
        EquipmentResponseDTO responseDTO = equipmentMapper.toDTO(updatedEquipment);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        equipmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
