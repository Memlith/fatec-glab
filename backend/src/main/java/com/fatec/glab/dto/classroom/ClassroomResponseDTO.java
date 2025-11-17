package com.fatec.glab.dto.classroom;

import java.util.List;

public record ClassroomResponseDTO(
        Long id,
        String name,
        Integer capacity,
        List<Long> equipmentsId,
        List<Long> softwaresId
) {
}
