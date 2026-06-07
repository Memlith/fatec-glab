package com.fatec.glab_mobile.data.repository

import android.util.Log
import com.fatec.glab_mobile.data.remote.GlabApiService
import com.fatec.glab_mobile.domain.model.Booking
import com.fatec.glab_mobile.domain.model.CreateBookingRequest
import com.fatec.glab_mobile.domain.model.Professor
import com.fatec.glab_mobile.domain.repository.BookingRepository
import com.fatec.glab_mobile.domain.repository.ProfessorRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "GlabRepo"

@Serializable
data class ApiErrorResponse(
    val message: String,
    val timestamp: String? = null
)

@Singleton
class BookingRepositoryImpl @Inject constructor(
    private val apiService: GlabApiService
) : BookingRepository {

    private val json = Json { ignoreUnknownKeys = true }

    private fun parseHttpError(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                val apiError = json.decodeFromString<ApiErrorResponse>(errorBody)
                apiError.message
            } else {
                "Erro HTTP ${e.code()}"
            }
        } catch (parseError: Exception) {
            "Erro HTTP ${e.code()}: ${e.message()}"
        }
    }

    override suspend fun getBookings(): Result<List<Booking>> {
        return try {
            Result.success(apiService.getBookings())
        } catch (e: HttpException) {
            Result.failure(Exception(parseHttpError(e)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBookingById(id: String): Result<Booking> {
        return try {
            Result.success(apiService.getBookingById(id))
        } catch (e: HttpException) {
            Result.failure(Exception(parseHttpError(e)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchBookings(date: String, roomId: String): Result<List<Booking>> {
        return try {
            Result.success(apiService.searchBookings(date, roomId))
        } catch (e: HttpException) {
            Result.failure(Exception(parseHttpError(e)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createBooking(request: CreateBookingRequest): Result<Booking> {
        return try {
            Result.success(apiService.createBooking(request))
        } catch (e: HttpException) {
            Result.failure(Exception(parseHttpError(e)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateBooking(id: String, request: CreateBookingRequest): Result<Booking> {
        return try {
            Result.success(apiService.updateBooking(id, request))
        } catch (e: HttpException) {
            Result.failure(Exception(parseHttpError(e)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteBooking(id: String): Result<Unit> {
        return try {
            apiService.deleteBooking(id)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(Exception(parseHttpError(e)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

@Singleton
class ProfessorRepositoryImpl @Inject constructor(
    private val apiService: GlabApiService
) : ProfessorRepository {

    override suspend fun getProfessors(): Result<List<Professor>> {
        return try {
            Result.success(apiService.getProfessors())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProfessorById(id: String): Result<Professor> {
        return try {
            Result.success(apiService.getProfessorById(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
