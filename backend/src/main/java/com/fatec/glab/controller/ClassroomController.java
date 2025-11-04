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

import com.fatec.glab.model.Classroom;
import com.fatec.glab.service.ClassroomService;

@RestController
<<<<<<< HEAD
@RequestMapping("/classrooms")
@CrossOrigin(origins = "http://localhost:3000")
public class ClassroomController {
=======
<<<<<<< HEAD:backend/src/main/java/com/fatec/glab/controller/LaboratoryController.java
@RequestMapping("/laboratories")
public class LaboratoryController {
=======
@RequestMapping("/classrooms")
@CrossOrigin(origins = "http://localhost:3000")
public class ClassroomController {
>>>>>>> b0e2203 (refactor: rename Laboratory>Classroom + new port):backend/src/main/java/com/fatec/glab/controller/ClassroomController.java
>>>>>>> e0318b6 (refactor: rename Laboratory>Classroom + new port)

    @Autowired
    private ClassroomService classroomService;

    @GetMapping
    public List<Classroom> getAll() {
        return classroomService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Classroom> getById(@PathVariable String id) {
        return classroomService.getById(id);
    }

    @PostMapping
    public Classroom create(@RequestBody Classroom classroom) {
        return classroomService.save(classroom);
    }

    @PutMapping("/{id}")
    public Classroom update(@PathVariable String id, @RequestBody Classroom classroom) {
        return classroomService.update(id, classroom);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        classroomService.delete(id);
    }

}
