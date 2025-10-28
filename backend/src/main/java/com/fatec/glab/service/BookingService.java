package com.fatec.glab.service;

import com.fatec.glab.exception.IdNotFoundException;
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


    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getById(String id) {
        return bookingRepository.findById(id);
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByDateAndRoom(LocalDate date, String room) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        return bookingRepository.findByRoomAndDateRange(room, startOfDay, endOfDay);
    }

    public void delete(String id){
        bookingRepository.deleteById(id);
    }

    public Booking update(String id, Booking updatedBooking){

        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()){
            Booking booking = existingBooking.get();
            booking.setStartTime(updatedBooking.getStartTime());
            booking.setEndTime(updatedBooking.getEndTime());
            booking.setNotes(updatedBooking.getNotes());
            booking.setUserId(updatedBooking.getUserId());
            booking.setResourceType(updatedBooking.getResourceType());
            booking.setResourceId(updatedBooking.getResourceId());
            booking.setRoom(updatedBooking.getRoom());
            return bookingRepository.save(booking);
        }else {
            throw new IdNotFoundException("Booking com ID" + id + " não foi encontrado. ");
        }

    }

}
