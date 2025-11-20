package com.fatec.glab.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fatec.glab.dto.booking.BookingRequestDTO;
import com.fatec.glab.dto.booking.BookingRequestUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.glab.exception.IdNotFoundException;
import com.fatec.glab.model.Booking;
import com.fatec.glab.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking save(BookingRequestDTO bookingDTO) {
        Booking booking = new Booking(bookingDTO);
        return bookingRepository.save(booking);
    }

    public Booking getById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Booking com ID" + id + " não foi encontrado. "));
    }

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingSearch(String roomId, LocalDate date) {

        if (roomId == null || date == null) {
            return List.of();
        }
        // Incrementar posteriomente os outros 2 metodos de pesquisa solo

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        return bookingRepository.findByRoomAndDateRange(roomId, startOfDay, endOfDay);


    }

    public Booking update(String id, BookingRequestUpdateDTO dto) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Booking com ID " + id + " não foi encontrado."));

        if (dto.startTime() != null) booking.setStartTime(dto.startTime());
        if (dto.endTime() != null) booking.setEndTime(dto.endTime());
        if (dto.type() != null) booking.setType(dto.type());
        if (dto.title() != null) booking.setTitle(dto.title());
        if (dto.description() != null) booking.setDescription(dto.description());
        if (dto.professorId() != null) booking.setProfessorId(dto.professorId());
        if (dto.room() != null) booking.setRoomId(dto.room());

        return bookingRepository.save(booking);
    }

    public void delete(String id) {
        bookingRepository.deleteById(id);
    }


}
