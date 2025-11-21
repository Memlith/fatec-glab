package com.fatec.glab.mapper;

import com.fatec.glab.dto.classroom.ClassroomRequestDTO;
import com.fatec.glab.dto.classroom.ClassroomRequestUpdateDTO;
import com.fatec.glab.dto.classroom.ClassroomResponseDTO;
import com.fatec.glab.model.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {

    ClassroomResponseDTO toDTO(Classroom classroom);

    List<ClassroomResponseDTO> toDTO(List<Classroom> classrooms);

    Classroom toEntity(ClassroomRequestDTO classroomRequestDTO);

    void updateFromDTO(ClassroomRequestUpdateDTO classroomRequestDTO, @MappingTarget Classroom classroom);
}
