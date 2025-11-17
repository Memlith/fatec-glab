package com.fatec.glab.dto.booking;

import com.fatec.glab.model.Booking;

import java.time.LocalDateTime;
import java.util.List;


public record BookingResponseDTO (
        String id,
        String title,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String userId,
        String type,
        String room
) {
    public BookingResponseDTO(Booking booking) {
        this(
                booking.getId(),
                booking.getTitle(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getUser(),
                booking.getType(),
                booking.getRoom()
        );
    }
}
