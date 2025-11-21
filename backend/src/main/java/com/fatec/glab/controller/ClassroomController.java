package com.fatec.glab.controller;

import com.fatec.glab.dto.classroom.ClassroomRequestDTO;
import com.fatec.glab.dto.classroom.ClassroomRequestUpdateDTO;
import com.fatec.glab.dto.classroom.ClassroomResponseDTO;
import com.fatec.glab.mapper.ClassroomMapper;
import com.fatec.glab.model.Classroom;
import com.fatec.glab.service.ClassroomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private ClassroomMapper classroomMapper;

    @PostMapping
    @Transactional
    public ResponseEntity<ClassroomResponseDTO> create(
            @Valid @RequestBody ClassroomRequestDTO classroomRequestDTO,
            UriComponentsBuilder uriBuilder) {

        Classroom savedClassroom = classroomService.save(classroomRequestDTO);

        URI location = uriBuilder
                .path("/classrooms/{id}").buildAndExpand(savedClassroom.getId()).toUri();

        ClassroomResponseDTO responseDTO = classroomMapper.toDTO(savedClassroom);
        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponseDTO> getById(@PathVariable String id) {
        Classroom classroom = classroomService.getById(id);
        ClassroomResponseDTO responseDTO = classroomMapper.toDTO(classroom);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClassroomResponseDTO>> getAll() {
        List<Classroom> classrooms = classroomService.getAll();
        List<ClassroomResponseDTO> responseDTOs = classroomMapper.toDTO(classrooms);
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ClassroomResponseDTO> update(
            @PathVariable String id,
            @Valid @RequestBody ClassroomRequestUpdateDTO classroomRequestDTO) {

        Classroom updatedClassroom = classroomService.update(id, classroomRequestDTO);
        ClassroomResponseDTO responseDTO = classroomMapper.toDTO(updatedClassroom);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        classroomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
