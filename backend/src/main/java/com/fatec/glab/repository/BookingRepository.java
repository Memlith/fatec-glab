package com.fatec.glab.repository;

import com.fatec.glab.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    @Query("""
    {
        "room": ?0,
        "startTime": { '$lt': ?2 },
        "endTime": { '$gt': ?1 }
    }
    """)
    List<Booking> findByRoomAndDateRange(String room, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
