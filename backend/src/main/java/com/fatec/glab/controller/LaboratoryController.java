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

import com.fatec.glab.model.Laboratory;
import com.fatec.glab.service.LaboratoryService;

@RestController
@RequestMapping("/laboratories")
@CrossOrigin(origins = "http://localhost:3000")
public class LaboratoryController {

    @Autowired
    private LaboratoryService laboratoryService;

    @GetMapping
    public List<Laboratory> getAll() {
        return laboratoryService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Laboratory> getById(@PathVariable String id) {
        return laboratoryService.getById(id);
    }

    @PostMapping
    public Laboratory create(@RequestBody Laboratory laboratory) {
        return laboratoryService.save(laboratory);
    }

    @PutMapping("/{id}")
    public Laboratory update(@PathVariable String id, @RequestBody Laboratory laboratory) {
        return laboratoryService.update(id, laboratory);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        laboratoryService.delete(id);
    }

}
