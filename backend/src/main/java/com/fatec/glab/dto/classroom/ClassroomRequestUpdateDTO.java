package com.fatec.glab.dto.classroom;

import java.util.List;

public record ClassroomRequestUpdateDTO(
        String name,
        Integer capacity,
        List<String> equipmentsId,
        List<String> softwaresId
) {
}
