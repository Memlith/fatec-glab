package com.fatec.glab_mobile.data.remote

import com.fatec.glab_mobile.domain.model.Booking
import com.fatec.glab_mobile.domain.model.CreateBookingRequest
import com.fatec.glab_mobile.domain.model.Classroom
import com.fatec.glab_mobile.domain.model.Equipment
import com.fatec.glab_mobile.domain.model.Professor
import com.fatec.glab_mobile.domain.model.Software
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GlabApiService {
    // Bookings
    @GET("bookings")
    suspend fun getBookings(): List<Booking>

    @GET("bookings/{id}")
    suspend fun getBookingById(@Path("id") id: String): Booking

    @GET("bookings/search")
    suspend fun searchBookings(
        @Query("date") date: String,
        @Query("roomId") roomId: String
    ): List<Booking>

    @POST("bookings")
    suspend fun createBooking(@Body request: CreateBookingRequest): Booking

    @PUT("bookings/{id}")
    suspend fun updateBooking(
        @Path("id") id: String,
        @Body request: CreateBookingRequest
    ): Booking

    @DELETE("bookings/{id}")
    suspend fun deleteBooking(@Path("id") id: String)

    // Professors
    @GET("professors")
    suspend fun getProfessors(): List<Professor>

    @GET("professors/{id}")
    suspend fun getProfessorById(@Path("id") id: String): Professor

    @POST("professors")
    suspend fun createProfessor(@Body professor: Professor): Professor

    @PUT("professors/{id}")
    suspend fun updateProfessor(
        @Path("id") id: String,
        @Body professor: Professor
    ): Professor

    @DELETE("professors/{id}")
    suspend fun deleteProfessor(@Path("id") id: String)

    // Classrooms
    @GET("classrooms")
    suspend fun getClassrooms(): List<Classroom>

    @GET("classrooms/{id}")
    suspend fun getClassroomById(@Path("id") id: Int): Classroom

    @POST("classrooms")
    suspend fun createClassroom(@Body classroom: Classroom): Classroom

    @PUT("classrooms/{id}")
    suspend fun updateClassroom(
        @Path("id") id: Int,
        @Body classroom: Classroom
    ): Classroom

    @DELETE("classrooms/{id}")
    suspend fun deleteClassroom(@Path("id") id: Int)

    // Equipment
    @GET("equipments")
    suspend fun getEquipments(): List<Equipment>

    @POST("equipments")
    suspend fun createEquipment(@Body equipment: Equipment): Equipment

    @PUT("equipments/{id}")
    suspend fun updateEquipment(
        @Path("id") id: String,
        @Body equipment: Equipment
    ): Equipment

    @DELETE("equipments/{id}")
    suspend fun deleteEquipment(@Path("id") id: String)

    // Softwares
    @GET("softwares")
    suspend fun getSoftwares(): List<Software>

    @POST("softwares")
    suspend fun createSoftware(@Body software: Software): Software

    @PUT("softwares/{id}")
    suspend fun updateSoftware(
        @Path("id") id: String,
        @Body software: Software
    ): Software

    @DELETE("softwares/{id}")
    suspend fun deleteSoftware(@Path("id") id: String)
}
