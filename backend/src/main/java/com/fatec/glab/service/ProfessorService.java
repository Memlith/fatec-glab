package com.fatec.glab.service;

import java.util.List;

import com.fatec.glab.dto.professor.ProfessorRequestDTO;
import com.fatec.glab.dto.professor.ProfessorRequestUpdateDTO;
import com.fatec.glab.mapper.ProfessorMapper;
import com.fatec.glab.model.Professor;
import com.fatec.glab.repository.ProfessorRepository;
import com.fatec.glab.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorMapper professorMapper;

    public Professor save(ProfessorRequestDTO professorRequestDTO) {
        Professor professor = professorMapper.toEntity(professorRequestDTO);
        return professorRepository.save(professor);
    }

    public Professor getById(String id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Professor com ID " + id + " não encontrado."));
    }

    public List<Professor> getAll() {
        return professorRepository.findAll();
    }

    public Professor update(String id, ProfessorRequestUpdateDTO professorRequestUpdateDTO) {
        Professor existingProfessor = professorRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Professor com ID " + id + " não encontrado."));

        professorMapper.updateFromDTO(professorRequestUpdateDTO, existingProfessor);

        return professorRepository.save(existingProfessor);
    }

    public void delete(String id) {
        professorRepository.deleteById(id);
    }
}
