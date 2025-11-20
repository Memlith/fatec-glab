package com.fatec.glab.mapper;

import com.fatec.glab.dto.booking.BookingRequestDTO;
import com.fatec.glab.dto.booking.BookingResponseDTO;
import com.fatec.glab.model.Booking;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingMapper {


    public BookingResponseDTO toDTO(Booking booking) {
        return new BookingResponseDTO(booking);
    }

    public List<BookingResponseDTO> toDTO(List<Booking> bookings) {
        return bookings.stream().map(this::toDTO).toList();
    }

}
