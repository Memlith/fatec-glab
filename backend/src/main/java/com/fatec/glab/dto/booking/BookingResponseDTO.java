package com.fatec.glab.dto.booking;

import com.fatec.glab.model.Booking;

import java.time.LocalDateTime;


public record BookingResponseDTO (
     String id,
     LocalDateTime startTime,
     LocalDateTime endTime,
     String professorId,
     String type,
     String title,
     String description,
     String roomId
) {
}
