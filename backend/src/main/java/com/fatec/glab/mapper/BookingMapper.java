package com.fatec.glab.mapper;

import com.fatec.glab.dto.booking.BookingRequestDTO;
import com.fatec.glab.dto.booking.BookingRequestUpdateDTO;
import com.fatec.glab.dto.booking.BookingResponseDTO;
import com.fatec.glab.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingResponseDTO toDTO(Booking booking);

    List<BookingResponseDTO> toDTO(List<Booking> bookings);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Booking toEntity(BookingRequestDTO bookingRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromDTO(BookingRequestUpdateDTO dto, @MappingTarget Booking booking);
}
