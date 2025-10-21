package com.fatec.glab.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.glab.model.Equipment;
import com.fatec.glab.exception.IdNotFoundException;
import com.fatec.glab.repository.EquipmentRepository;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> getAll() {
        return equipmentRepository.findAll();
    }

    public Optional<Equipment> getById(String id) {
        return equipmentRepository.findById(id);
    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public Equipment update(String id, Equipment updatedEquipment) {
        Optional<Equipment> existingEquipment = equipmentRepository.findById(id);
        if (existingEquipment.isPresent()) {
            Equipment equipment = existingEquipment.get();
            equipment.setName(updatedEquipment.getName());
            equipment.setPrice(updatedEquipment.getPrice());
            equipment.setDescription(updatedEquipment.getDescription());
            equipment.setStock(updatedEquipment.getStock());
            return equipmentRepository.save(equipment);
        } else {
            throw new IdNotFoundException("Equipment com ID " + id + " não encontrado.");

        }
    }

    public void delete(String id) {
        equipmentRepository.deleteById(id);
    }

}
