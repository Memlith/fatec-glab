package com.fatec.glab.service;

import java.util.List;
import java.util.Optional;

import com.fatec.glab.model.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.glab.dto.user.ProfessorResponseDTO;
import com.fatec.glab.exception.IdNotFoundException;
import com.fatec.glab.repository.ProfessorRepository;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public List<ProfessorResponseDTO> getAll() {
        List<Professor> professor = professorRepository.findAll();
        return professor.stream().map(this::convertToUserResponseDTO).toList();
    }

    public ProfessorResponseDTO getById(String id) {
        Optional<Professor> user = professorRepository.findById(id);
        if (user.isEmpty()) {
            throw new IdNotFoundException("User com ID " + id + " não encontrado.");
        }

        return convertToUserResponseDTO(user.get());

    }

    public ProfessorResponseDTO save(Professor professor) {
        return convertToUserResponseDTO(professorRepository.save(professor));
    }

    public ProfessorResponseDTO update(String id, Professor updatedProfessor) {
        Optional<Professor> existingUser = professorRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new IdNotFoundException("User com ID " + id + " não encontrado.");
        }

        Professor professor = existingUser.get();
        professor.setName(updatedProfessor.getName());
        professor.setEmail(updatedProfessor.getEmail());
        return convertToUserResponseDTO(professorRepository.save(professor));

    }

    public void delete(String id) {
        Optional<Professor> existingUser = professorRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new IdNotFoundException("User com ID " + id + " não encontrado.");
        }
        professorRepository.deleteById(id);
    }

    private ProfessorResponseDTO convertToUserResponseDTO(Professor professor) {
        return new ProfessorResponseDTO(
                professor.getId(),
                professor.getName(),
                professor.getEmail()
        );
    }

}
