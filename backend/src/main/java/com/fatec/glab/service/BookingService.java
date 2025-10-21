package com.fatec.glab.service;

import com.fatec.glab.model.Booking;
import com.fatec.glab.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;


    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> findById(String id) {
        return bookingRepository.findById(id);
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> findBookingsByDateAndRoom(LocalDate date, String room) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        return bookingRepository.findByRoomAndDateRange(room, startOfDay, endOfDay);
    }

}
