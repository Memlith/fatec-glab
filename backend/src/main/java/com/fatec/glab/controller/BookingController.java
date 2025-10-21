package com.fatec.glab.controller;

import com.fatec.glab.model.Booking;
import com.fatec.glab.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{id}")
    public Optional<Booking> findById(@PathVariable String id) {
        return bookingService.findById(id);
    }

    @GetMapping
    public List<Booking> findBookings(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,

            @RequestParam(value = "room", required = false) String room) {

        if (date != null && room != null) {
            return bookingService.findBookingsByDateAndRoom(date, room);
        } else {
            return bookingService.findAll();
        }
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping
    public Booking save(@RequestBody Booking booking) {
        return bookingService.save(booking);
    }

}
