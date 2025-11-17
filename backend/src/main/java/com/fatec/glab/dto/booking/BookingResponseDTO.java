package com.fatec.glab.dto.booking;

import java.time.LocalDateTime;
import java.util.List;


public record BookingResponseDTO (
        Long id,
        String title,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long userId,
        String type,
        List<String> resource
) {
}
