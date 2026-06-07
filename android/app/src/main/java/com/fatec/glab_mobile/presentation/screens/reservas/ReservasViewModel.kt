package com.fatec.glab_mobile.presentation.screens.reservas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.glab_mobile.domain.model.Booking
import com.fatec.glab_mobile.domain.model.Professor
import com.fatec.glab_mobile.domain.model.Room
import com.fatec.glab_mobile.domain.model.RoomsData
import com.fatec.glab_mobile.domain.repository.BookingRepository
import com.fatec.glab_mobile.domain.repository.ProfessorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class ReservasUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedRoom: Room = Room(id = "lab_01", label = "Lab 01"),
    val bookings: List<Booking> = emptyList(),
    val professors: List<Professor> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val roomsData: RoomsData = RoomsData(),
    val selectedBuilding: String = "1",
    val selectedFloor: String = "terreo"
)

@HiltViewModel
class ReservasViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val professorRepository: ProfessorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReservasUiState())
    val uiState: StateFlow<ReservasUiState> = _uiState.asStateFlow()

    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    init {
        loadRoomsData()
        loadProfessors()
        loadBookings()
    }

    private fun loadRoomsData() {
        val roomsJson = """
        {
            "building1": {
                "terreo": [
                    {"id": "lab_01", "label": "Lab 01", "dbId": "692a30fba4a35f100361d9a3", "x": 50, "y": 690, "width": 700, "height": 600},
                    {"id": "lab_02", "label": "Lab 02", "dbId": "692a30fea4a35f100361d9a4", "x": 50, "y": 50, "width": 800, "height": 600},
                    {"id": "lab_03", "label": "Lab 03", "dbId": "692a30ffa4a35f100361d9a5", "x": 890, "y": 50, "width": 800, "height": 600},
                    {"id": "lab_04", "label": "Lab 04", "dbId": "692a3101a4a35f100361d9a6", "x": 990, "y": 690, "width": 700, "height": 600},
                    {"id": "lab_05", "label": "Lab 05", "dbId": "692a3102a4a35f100361d9a7", "x": 50, "y": 1800, "width": 700, "height": 525},
                    {"id": "lab_06", "label": "Lab 06", "dbId": "692a3104a4a35f100361d9a8", "x": 50, "y": 2370, "width": 800, "height": 600},
                    {"id": "lab_07", "label": "Lab 07", "dbId": "692a3105a4a35f100361d9a9", "x": 890, "y": 2370, "width": 800, "height": 600},
                    {"id": "lab_08", "label": "Lab 08", "dbId": "692a3107a4a35f100361d9aa", "x": 990, "y": 1800, "width": 700, "height": 525}
                ]
            },
            "building2": {
                "terreo": [
                    {"id": "lab_12", "label": "Lab 12", "dbId": "692a3110a4a35f100361d9ae", "x": 100, "y": 940, "width": 600, "height": 800},
                    {"id": "lab_11", "label": "Lab 11", "dbId": "692a310fa4a35f100361d9ad", "x": 100, "y": 1800, "width": 600, "height": 800},
                    {"id": "lab_13", "label": "Lab 13", "dbId": "692a3112a4a35f100361d9af", "x": 100, "y": 100, "width": 800, "height": 600}
                ],
                "primeiro": [
                    {"id": "11", "label": "Sala 11", "x": 100, "y": 1800, "width": 600, "height": 800},
                    {"id": "12", "label": "Sala 12", "x": 100, "y": 940, "width": 600, "height": 800},
                    {"id": "13", "label": "Sala 13", "x": 100, "y": 100, "width": 800, "height": 600},
                    {"id": "14", "label": "Sala 14", "x": 940, "y": 1800, "width": 600, "height": 800},
                    {"id": "15", "label": "Sala 15", "x": 940, "y": 940, "width": 600, "height": 800},
                    {"id": "16", "label": "Sala 16", "x": 940, "y": 100, "width": 600, "height": 800}
                ],
                "segundo": [
                    {"id": "21", "label": "Sala 21", "x": 100, "y": 1800, "width": 600, "height": 800},
                    {"id": "22", "label": "Sala 22", "x": 100, "y": 940, "width": 600, "height": 800},
                    {"id": "23", "label": "Sala 23", "x": 100, "y": 100, "width": 800, "height": 600},
                    {"id": "24", "label": "Sala 24", "x": 940, "y": 1800, "width": 600, "height": 800},
                    {"id": "25", "label": "Sala 25", "x": 940, "y": 940, "width": 600, "height": 800},
                    {"id": "26", "label": "Sala 26", "x": 940, "y": 100, "width": 600, "height": 800}
                ],
                "terceiro": [
                    {"id": "aud", "label": "Auditório", "x": 100, "y": 100, "width": 1400, "height": 1200},
                    {"id": "31", "label": "Sala 31", "x": 100, "y": 1800, "width": 600, "height": 800},
                    {"id": "32", "label": "Sala 32", "x": 940, "y": 1800, "width": 600, "height": 800}
                ]
            },
            "building3": {
                "terreo": [
                    {"id": "lab_09", "label": "Lab 09", "dbId": "692a3109a4a35f100361d9ab", "x": 100, "y": 1040, "width": 600, "height": 800},
                    {"id": "lab_10", "label": "Lab 10", "dbId": "692a310ba4a35f100361d9ac", "x": 100, "y": 100, "width": 800, "height": 900}
                ]
            }
        }
        """.trimIndent()

        try {
            val roomsData = json.decodeFromString<RoomsData>(roomsJson)
            _uiState.update { it.copy(roomsData = roomsData) }
        } catch (e: Exception) {
            // Handle parse error silently
        }
    }

    private fun loadProfessors() {
        viewModelScope.launch {
            professorRepository.getProfessors()
                .onSuccess { professors ->
                    _uiState.update { it.copy(professors = professors) }
                }
                .onFailure { /* Ignore errors for professors */ }
        }
    }

    fun loadBookings() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val dateStr = _uiState.value.selectedDate
                .format(DateTimeFormatter.ISO_LOCAL_DATE)
            val roomId = _uiState.value.selectedRoom.id

            bookingRepository.searchBookings(dateStr, roomId)
                .onSuccess { bookings ->
                    val professors = _uiState.value.professors
                    val bookingsWithProfessorNames = bookings.map { booking ->
                        val professorName = professors.find { it.id == booking.professorId }?.name
                        booking.copy(professorName = professorName)
                    }
                    _uiState.update { it.copy(bookings = bookingsWithProfessorNames, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(error = error.message, isLoading = false) }
                }
        }
    }

    fun selectDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        loadBookings()
    }

    fun selectRoom(room: Room) {
        _uiState.update { it.copy(selectedRoom = room) }
        loadBookings()
    }

    fun selectBuilding(building: String) {
        _uiState.update { it.copy(selectedBuilding = building, selectedFloor = "terreo") }
    }

    fun selectFloor(floor: String) {
        _uiState.update { it.copy(selectedFloor = floor) }
    }

    fun getCurrentRooms(): List<Room> {
        val state = _uiState.value
        val building = when (state.selectedBuilding) {
            "1" -> state.roomsData.building1
            "2" -> state.roomsData.building2
            "3" -> state.roomsData.building3
            else -> state.roomsData.building1
        }
        return when (state.selectedFloor) {
            "terreo" -> building.terreo
            "primeiro" -> building.primeiro
            "segundo" -> building.segundo
            "terceiro" -> building.terceiro
            else -> emptyList()
        }
    }

    fun getAvailableFloors(): List<String> {
        val state = _uiState.value
        val building = when (state.selectedBuilding) {
            "1" -> state.roomsData.building1
            "2" -> state.roomsData.building2
            "3" -> state.roomsData.building3
            else -> state.roomsData.building1
        }
        return when {
            building.terceiro.isNotEmpty() -> listOf("terreo", "primeiro", "segundo", "terceiro")
            building.segundo.isNotEmpty() -> listOf("terreo", "primeiro", "segundo")
            building.primeiro.isNotEmpty() -> listOf("terreo", "primeiro")
            else -> listOf("terreo")
        }
    }

    fun getFormattedDate(): String {
        val date = _uiState.value.selectedDate
        val formatter = java.time.format.DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", java.util.Locale("pt", "BR"))
        return date.format(formatter).replaceFirstChar { it.uppercase() }
    }

    fun deleteBooking(bookingId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            bookingRepository.deleteBooking(bookingId)
                .onSuccess {
                    loadBookings()
                    onSuccess()
                }
                .onFailure { error ->
                    onError(error.message ?: "Erro ao deletar reserva")
                }
        }
    }

    fun updateBooking(
        bookingId: String,
        title: String,
        description: String,
        type: String,
        professorId: String,
        roomId: String,
        startTime: String,
        endTime: String,
        repeat: Boolean,
        date: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val request = com.fatec.glab_mobile.domain.model.CreateBookingRequest(
                startTime = "${date}T${startTime}:00",
                endTime = "${date}T${endTime}:00",
                professorId = professorId,
                type = type,
                title = title,
                description = description.ifBlank { null },
                roomId = roomId,
                repeat = repeat
            )

            bookingRepository.updateBooking(bookingId, request)
                .onSuccess {
                    loadBookings()
                    onSuccess()
                }
                .onFailure { error ->
                    onError(error.message ?: "Erro ao atualizar reserva")
                }
        }
    }
}
