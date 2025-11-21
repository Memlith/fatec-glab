package com.fatec.glab.dto.classroom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ClassroomRequestDTO(

        @NotBlank
        String name,

        @NotNull
        Integer capacity,

        List<String> equipmentsId,
        List<String> softwaresId


) {
}
