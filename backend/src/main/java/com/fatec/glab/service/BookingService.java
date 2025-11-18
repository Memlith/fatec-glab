package com.fatec.glab.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.fatec.glab.dto.booking.BookingRequestUpdateDTO;
import com.fatec.glab.dto.booking.BookingResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.glab.exception.IdNotFoundException;
import com.fatec.glab.model.Booking;
import com.fatec.glab.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public BookingResponseDTO getById(String id) {

        var booking = bookingRepository.findById(id);
        if (booking.isEmpty()) {
            throw new IdNotFoundException("Booking com ID" + id + " não foi encontrado. ");
        }

        return new BookingResponseDTO(booking.get());
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByDateAndRoom(LocalDate date, String room) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        return bookingRepository.findByRoomAndDateRange(room, startOfDay, endOfDay);
    }

    public void delete(String id) {
        bookingRepository.deleteById(id);
    }

/* <<<<<<<<<<<<<<  ✨ Windsurf Command ⭐ >>>>>>>>>>>>>>>> */
    /**
     * Update a booking with the given id and data.
     *
     * @param id the id of the booking to update
     * @param dto the data to update the booking with
     * @return the updated booking
     * @throws IdNotFoundException if the booking with the given id is not found
     */
/* <<<<<<<<<<  039ecffe-5d4d-4b1f-a57b-72f1cf4c5b00  >>>>>>>>>>> */
    public BookingResponseDTO update(String id, BookingRequestUpdateDTO dto) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Booking com ID " + id + " não foi encontrado."));

        if (dto.startTime() != null) booking.setStartTime(dto.startTime());
        if (dto.endTime() != null) booking.setEndTime(dto.endTime());
        if (dto.type() != null) booking.setType(dto.type());
        if (dto.title() != null) booking.setTitle(dto.title());
        if (dto.description() != null) booking.setDescription(dto.description());
        if (dto.user() != null) booking.setUser(dto.user());
        if (dto.room() != null) booking.setRoom(dto.room());

        Booking saved = bookingRepository.save(booking);
        return new BookingResponseDTO(saved);
    }

}
