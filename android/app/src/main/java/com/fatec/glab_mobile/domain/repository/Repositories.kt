package com.fatec.glab_mobile.domain.repository

import com.fatec.glab_mobile.domain.model.Booking
import com.fatec.glab_mobile.domain.model.CreateBookingRequest
import com.fatec.glab_mobile.domain.model.Professor

interface BookingRepository {
    suspend fun getBookings(): Result<List<Booking>>
    suspend fun getBookingById(id: String): Result<Booking>
    suspend fun searchBookings(date: String, roomId: String): Result<List<Booking>>
    suspend fun createBooking(request: CreateBookingRequest): Result<Booking>
    suspend fun updateBooking(id: String, request: CreateBookingRequest): Result<Booking>
    suspend fun deleteBooking(id: String): Result<Unit>
}

interface ProfessorRepository {
    suspend fun getProfessors(): Result<List<Professor>>
    suspend fun getProfessorById(id: String): Result<Professor>
}
