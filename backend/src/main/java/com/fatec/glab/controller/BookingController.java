package com.fatec.glab.controller;

import com.fatec.glab.dto.booking.BookingResponseDTO;
import com.fatec.glab.model.Booking;
import com.fatec.glab.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        var booking = bookingService.getById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping
    public List<Booking> findBookings(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,

            @RequestParam(value = "room", required = false) String room) {

        if (date != null && room != null) {
            return bookingService.getBookingsByDateAndRoom(date, room);
        } else {
            return bookingService.getAll();
        }
    }

    @PostMapping
    public Booking save(@RequestBody Booking booking) {
        return bookingService.save(booking);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        bookingService.delete(id);
    }

    @PutMapping("/{id}")
    public Booking update(@PathVariable String id, @RequestBody Booking booking){
        return bookingService.update(id, booking);
    }


}
