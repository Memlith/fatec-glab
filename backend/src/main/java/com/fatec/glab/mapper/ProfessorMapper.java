package com.fatec.glab.mapper;

import com.fatec.glab.dto.professor.ProfessorRequestDTO;
import com.fatec.glab.dto.professor.ProfessorRequestUpdateDTO;
import com.fatec.glab.dto.professor.ProfessorResponseDTO;
import com.fatec.glab.model.Professor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

    ProfessorResponseDTO toDTO(Professor professor);

    List<ProfessorResponseDTO> toDTO(List<Professor> professors);

    @Mapping(target = "id", ignore = true)
    Professor toEntity(ProfessorRequestDTO professorRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateFromDTO(ProfessorRequestUpdateDTO professorRequestUpdateDTO, @MappingTarget Professor professor);
}
