package com.fatec.glab.mapper;

import com.fatec.glab.dto.professor.ProfessorRequestDTO;
import com.fatec.glab.dto.professor.ProfessorRequestUpdateDTO;
import com.fatec.glab.dto.professor.ProfessorResponseDTO;
import com.fatec.glab.model.Professor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

    ProfessorResponseDTO toDTO(Professor professor);

    List<ProfessorResponseDTO> toDTO(List<Professor> professors);

    Professor toEntity(ProfessorRequestDTO professorRequestDTO);

    void updateFromDTO(ProfessorRequestUpdateDTO professorRequestUpdateDTO, Professor professor);
}
