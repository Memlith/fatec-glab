package com.fatec.glab.controller;

import com.fatec.glab.dto.professor.ProfessorRequestDTO;
import com.fatec.glab.dto.professor.ProfessorRequestUpdateDTO;
import com.fatec.glab.dto.professor.ProfessorResponseDTO;
import com.fatec.glab.mapper.ProfessorMapper;
import com.fatec.glab.model.Professor;
import com.fatec.glab.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorMapper professorMapper;

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> getAll() {
        List<Professor> professors = professorService.getAll();
        List<ProfessorResponseDTO> responseDTOs = professorMapper.toDTO(professors);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getById(@PathVariable String id) {
        Professor professor = professorService.getById(id);
        ProfessorResponseDTO responseDTO = professorMapper.toDTO(professor);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> create(@RequestBody ProfessorRequestDTO professorRequestDTO) {
        Professor savedProfessor = professorService.save(professorRequestDTO);
        ProfessorResponseDTO responseDTO = professorMapper.toDTO(savedProfessor);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> update(@PathVariable String id, @RequestBody ProfessorRequestUpdateDTO professorRequestDTO) {
        Professor updatedProfessor = professorService.update(id, professorRequestDTO);
        ProfessorResponseDTO responseDTO = professorMapper.toDTO(updatedProfessor);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
