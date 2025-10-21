package com.fatec.glab.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.glab.model.Equipment;
import com.fatec.glab.service.EquipmentService;

@RestController
@RequestMapping("/equipments")
@CrossOrigin(origins = "http://localhost:3000")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public List<Equipment> getAll() {
        return equipmentService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Equipment> getById(@PathVariable String id) {
        return equipmentService.getById(id);
    }

    @PostMapping
    public Equipment create(@RequestBody Equipment equipment) {
        return equipmentService.save(equipment);
    }

    @PutMapping("/{id}")
    public Equipment update(@PathVariable String id, @RequestBody Equipment equipment) {
        return equipmentService.update(id, equipment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        equipmentService.delete(id);
    }

}
