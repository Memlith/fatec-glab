package com.fatec.glab.dto.booking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public record BookingRequestDTO(
        @NotBlank
        String title,

        @NotNull()
        LocalDateTime startTime,

        @NotNull()
        LocalDateTime endTime,

        @NotNull
        Long userId,

        String type,
        List<String> resource
    ){
}
