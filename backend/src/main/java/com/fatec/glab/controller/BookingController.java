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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @Transactional
    public ResponseEntity<BookingResponseDTO> save(
            @Valid @RequestBody BookingRequestDTO bookingRequestDTO,
            UriComponentsBuilder uriBuilder) {

        Booking savedBooking = bookingService.save(bookingRequestDTO);

        URI location = uriBuilder
                .path("/bookings/{id}")
                .buildAndExpand(savedBooking.getId())
                .toUri();

        BookingResponseDTO responseDTO = bookingMapper.toDTO(savedBooking);

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getById(@PathVariable String id) {
        Booking booking = bookingService.getById(id);
        BookingResponseDTO responseDTO = bookingMapper.toDTO(booking);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAll() {
        List<Booking> bookings = bookingService.getAll();
        List<BookingResponseDTO> responseDTOs = bookingMapper.toDTO(bookings);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookingResponseDTO>> search(
            @RequestParam(value = "roomId", required = false) String roomId,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<Booking> bookings = bookingService.getBookingSearch(roomId, date);
        List<BookingResponseDTO> responseDTOs = bookingMapper.toDTO(bookings);
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<BookingResponseDTO> update(
            @PathVariable String id,
            @Valid @RequestBody BookingRequestUpdateDTO bookingUpdateDTO) {

        Booking updatedBooking = bookingService.update(id, bookingUpdateDTO);
        BookingResponseDTO responseDTO = bookingMapper.toDTO(updatedBooking);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
