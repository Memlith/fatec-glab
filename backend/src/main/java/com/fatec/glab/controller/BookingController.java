package com.fatec.glab.controller;

import com.fatec.glab.dto.booking.BookingRequestDTO;
import com.fatec.glab.dto.booking.BookingRequestUpdateDTO;
import com.fatec.glab.dto.booking.BookingResponseDTO;
import com.fatec.glab.mapper.BookingMapper;
import com.fatec.glab.model.Booking;
import com.fatec.glab.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @Transactional
    public ResponseEntity<BookingResponseDTO> save(@Valid @RequestBody BookingRequestDTO bookingRequestDTO, UriComponentsBuilder uriBuilder) {
        Booking booking = bookingService.save(bookingRequestDTO);

        URI uri = uriBuilder.path("/bookings/{id}").buildAndExpand(booking.getId()).toUri();

        return ResponseEntity.created(uri).body(bookingMapper.toDTO(booking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getById(@PathVariable String id) {
        Booking booking = bookingService.getById(id);
        return ResponseEntity.ok(bookingMapper.toDTO(booking));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> findBookings() {
        List<Booking> booking = bookingService.getAll();
        return ResponseEntity.ok(bookingMapper.toDTO(booking));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookingResponseDTO>> findBookingsForQuery(
            @RequestParam(value = "roomId", required = false) String roomId,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<Booking> bookings = bookingService.getBookingSearch(roomId, date);

        return ResponseEntity.ok(bookingMapper.toDTO(bookings));
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<BookingResponseDTO> update(@PathVariable String id, @Valid @RequestBody BookingRequestUpdateDTO booking) {
        var bookingUpdated = bookingService.update(id, booking);
        return ResponseEntity.ok(bookingMapper.toDTO(bookingUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        bookingService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
