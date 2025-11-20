package com.fatec.glab.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public record BookingRequestDTO(

        @NotNull()
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startTime,

        @NotNull()
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime endTime,

        @NotNull()
        Boolean repeat,

        String type,

        @NotBlank
        String title,

        String description,

        @NotNull
        String professorId,

        @NotBlank
        String roomId
) {
}
