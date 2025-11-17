package com.fatec.glab.controller;

import java.util.List;

import com.fatec.glab.dto.professor.ProfessorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.glab.model.Professor;
import com.fatec.glab.service.ProfessorService;

@RestController
@RequestMapping("/users")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.professorService.getAll()) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getById(@PathVariable String id) {

        return ResponseEntity.status(HttpStatus.OK).body(this.professorService.getById(id));

    }

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> create(@RequestBody Professor professor) {
        ProfessorResponseDTO professorResponseDTO = professorService.save(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> update(@PathVariable String id, @RequestBody Professor professor) {

        return ResponseEntity.status(HttpStatus.OK).body(this.professorService.update(id, professor));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {

        this.professorService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deletado com sucesso.");

    }
}
