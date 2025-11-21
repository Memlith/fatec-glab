package com.fatec.glab.mapper;

import com.fatec.glab.dto.classroom.ClassroomResponseDTO;
import com.fatec.glab.model.Classroom;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClassroomMapper {
    public ClassroomResponseDTO toDTO(Classroom classroom) {
        return new ClassroomResponseDTO(classroom);
    }

    public List<ClassroomResponseDTO> toDTO(List<Classroom> classroom) {
        return classroom.stream().map(this::toDTO).toList();
    }
}
