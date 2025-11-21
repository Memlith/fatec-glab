package com.fatec.glab.dto.classroom;

import com.fatec.glab.model.Classroom;

import java.util.List;

public record ClassroomResponseDTO(
        String id,
        String name,
        Integer capacity,
        List<String> equipmentsId,
        List<String> softwaresId
) {
    public ClassroomResponseDTO(Classroom classroom) {
        this(
                classroom.getId(),
                classroom.getName(),
                classroom.getCapacity(),
                classroom.getEquipmentsId(),
                classroom.getSoftwaresId());
    }
}
