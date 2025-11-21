package com.fatec.glab.mapper;

import com.fatec.glab.dto.booking.BookingRequestDTO;
import com.fatec.glab.dto.booking.BookingRequestUpdateDTO;
import com.fatec.glab.dto.booking.BookingResponseDTO;
import com.fatec.glab.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingResponseDTO toDTO(Booking booking);

    List<BookingResponseDTO> toDTO(List<Booking> bookings);

    Booking toEntity(BookingRequestDTO bookingRequestDTO);

    void updateFromDTO(BookingRequestUpdateDTO dto, @MappingTarget Booking booking);

}
