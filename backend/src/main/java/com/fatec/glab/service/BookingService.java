package com.fatec.glab.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fatec.glab.dto.booking.BookingRequestDTO;
import com.fatec.glab.dto.booking.BookingRequestUpdateDTO;
import com.fatec.glab.mapper.BookingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.glab.exception.BookingAlreadyExistsException;
import com.fatec.glab.exception.IdNotFoundException;
import com.fatec.glab.model.Booking;
import com.fatec.glab.repository.BookingRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMapper bookingMapper;

    @Transactional
    public Booking save(BookingRequestDTO bookingDTO) {
        Booking novoBooking = bookingMapper.toEntity(bookingDTO);
        getBookingSearch(novoBooking.getRoomId(), novoBooking.getStartTime().toLocalDate())
                .forEach(existingBooking -> {
                    boolean overlaps = novoBooking.getStartTime().isBefore(existingBooking.getEndTime())
                            && novoBooking.getEndTime().isAfter(existingBooking.getStartTime());
                    if (overlaps) {
                        throw new BookingAlreadyExistsException("Já existe uma reserva para este horário.");
                    }
                });
        return bookingRepository.save(novoBooking);
    }

    public Booking getById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(()
                        -> new IdNotFoundException("Booking com ID " + id + " não foi encontrado.")
                );
    }

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingSearch(String roomId, LocalDate date) {

        if (roomId == null || date == null) {
            return List.of();
        }

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        return bookingRepository.findByRoomAndDateRange(roomId, startOfDay, endOfDay);
    }

    @Transactional
    public Booking update(String id, BookingRequestUpdateDTO dto) {

        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(()
                        -> new IdNotFoundException("Booking com ID " + id + " não foi encontrado.")
                );

        bookingMapper.updateFromDTO(dto, existingBooking);

        return bookingRepository.save(existingBooking);
    }

    @Transactional
    public void delete(String id) {
        bookingRepository.deleteById(id);
    }
}
