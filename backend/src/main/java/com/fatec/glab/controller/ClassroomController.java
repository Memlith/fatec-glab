package com.fatec.glab.controller;

import java.util.List;
import java.util.Optional;

import com.fatec.glab.dto.classroom.ClassroomRequestDTO;
import com.fatec.glab.dto.classroom.ClassroomResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping
    public ResponseEntity<ClassroomResponseDTO> create(@RequestBody ClassroomRequestDTO classroomRequestDTO) {
        ClassroomResponseDTO classroom = new Classroom(classroomRequestDTO);
        return classroomService.save(classroom);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponseDTO> getById(@PathVariable String id) {
        return classroomService.getById(id);
    }

    @GetMapping
    public ResponseEntity<List<ClassroomResponseDTO>> getAll() {
        return classroomService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classroom> update(@PathVariable String id, @RequestBody Classroom classroom) {
        return classroomService.update(id, classroom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        classroomService.delete(id);
    }

}