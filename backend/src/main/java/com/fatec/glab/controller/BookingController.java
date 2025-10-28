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
    public Optional<Booking> getById(@PathVariable String id) {
        return bookingService.getById(id);
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
