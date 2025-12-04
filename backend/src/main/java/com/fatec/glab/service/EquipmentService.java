package com.fatec.glab.service;

import java.util.List;

import com.fatec.glab.dto.equipment.EquipmentRequestDTO;
import com.fatec.glab.dto.equipment.EquipmentRequestUpdateDTO;
import com.fatec.glab.mapper.EquipmentMapper;
import com.fatec.glab.model.Equipment;
import com.fatec.glab.repository.EquipmentRepository;
import com.fatec.glab.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Transactional
    public Equipment save(EquipmentRequestDTO equipmentRequestDTO) {
        Equipment equipment = equipmentMapper.toEntity(equipmentRequestDTO);
        return equipmentRepository.save(equipment);
    }

    public Equipment getById(String id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Equipment com ID " + id + " não encontrado."));
    }

    public List<Equipment> getAll() {
        return equipmentRepository.findAll();
    }

    @Transactional
    public Equipment update(String id, EquipmentRequestUpdateDTO equipmentRequestUpdateDTO) {
        Equipment existingEquipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Equipment com ID " + id + " não encontrado."));

        equipmentMapper.updateFromDTO(equipmentRequestUpdateDTO, existingEquipment);

        return equipmentRepository.save(existingEquipment);
    }

    @Transactional
    public void delete(String id) {
        equipmentRepository.deleteById(id);
    }
}
