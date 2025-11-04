package com.fatec.glab.controller;

import com.fatec.glab.model.Laboratory;
import com.fatec.glab.model.Software;
import com.fatec.glab.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/softwares")
public class SoftwareController {

    @Autowired
    private SoftwareService softwareService;

    @GetMapping
    public List<Software> getAll() {
        return softwareService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Software> getById(@PathVariable String id) {
        return softwareService.getById(id);
    }

    @PostMapping
    public Software create(@RequestBody Software software) {
        return softwareService.save(software);
    }

    @PutMapping("/{id}")
    public Software update(@PathVariable String id, @RequestBody Software software) {
        return softwareService.update(id, software);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        softwareService.delete(id);
    }
}
