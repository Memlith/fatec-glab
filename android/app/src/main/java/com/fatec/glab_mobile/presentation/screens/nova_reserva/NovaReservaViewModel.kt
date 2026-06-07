package com.fatec.glab_mobile.presentation.screens.nova_reserva

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.glab_mobile.domain.model.CreateBookingRequest
import com.fatec.glab_mobile.domain.model.Professor
import com.fatec.glab_mobile.domain.repository.BookingRepository
import com.fatec.glab_mobile.domain.repository.ProfessorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NovaReservaUiState(
    val title: String = "",
    val description: String = "",
    val type: String = "",
    val professorId: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val repeat: Boolean = false,
    val professors: List<Professor> = emptyList(),
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val titleError: String? = null,
    val typeError: String? = null,
    val professorError: String? = null,
    val startTimeError: String? = null,
    val endTimeError: String? = null
)

data class CourseOption(
    val value: String,
    val label: String,
    val isCourse: Boolean = true
)

val COURSE_OPTIONS = listOf(
    CourseOption("agendamento", "Agendamento", false),
    CourseOption("DSM", "DSM - Desenvolvimento de Software Multiplataforma"),
    CourseOption("COMEX", "Comércio Exterior"),
    CourseOption("REDES", "Redes de Computadores"),
    CourseOption("ADS", "Análise e Desenvolvimento de Sistemas"),
    CourseOption("GESTAO-EMP-V", "Gestão Empresarial Vespertino"),
    CourseOption("GESTAO-EMP-N", "Gestão Empresarial Noturno"),
    CourseOption("GESTAO-SERVICOS", "Gestão de Serviços"),
    CourseOption("LOG-AERO", "Logística Aeroportuária")
)

@HiltViewModel
class NovaReservaViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val professorRepository: ProfessorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NovaReservaUiState())
    val uiState: StateFlow<NovaReservaUiState> = _uiState.asStateFlow()

    init {
        loadProfessors()
    }

    private fun loadProfessors() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            professorRepository.getProfessors()
                .onSuccess { professors ->
                    _uiState.update { it.copy(professors = professors, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(error = error.message, isLoading = false) }
                }
        }
    }

    fun reloadProfessors() {
        loadProfessors()
    }

    fun updateTitle(value: String) {
        _uiState.update { it.copy(title = value, titleError = null) }
    }

    fun updateDescription(value: String) {
        _uiState.update { it.copy(description = value) }
    }

    fun updateType(value: String) {
        _uiState.update { it.copy(type = value, typeError = null) }
    }

    fun updateProfessor(value: String) {
        _uiState.update { it.copy(professorId = value, professorError = null) }
    }

    fun updateStartTime(value: String) {
        _uiState.update { it.copy(startTime = value, startTimeError = null) }
    }

    fun updateEndTime(value: String) {
        _uiState.update { it.copy(endTime = value, endTimeError = null) }
    }

    fun updateRepeat(value: Boolean) {
        _uiState.update { it.copy(repeat = value) }
    }

    fun submitBooking(date: String, roomId: String, onSuccess: () -> Unit) {
        val state = _uiState.value

        // Validation
        var hasError = false

        if (state.title.length < 2) {
            _uiState.update { it.copy(titleError = "O título deve ter no mínimo 2 caracteres") }
            hasError = true
        }

        if (state.type.isBlank()) {
            _uiState.update { it.copy(typeError = "Selecione o tipo do curso") }
            hasError = true
        }

        if (state.professorId.isBlank()) {
            _uiState.update { it.copy(professorError = "Selecione o professor") }
            hasError = true
        }

        if (!isValidTime(state.startTime)) {
            _uiState.update { it.copy(startTimeError = "Formato inválido (HH:MM)") }
            hasError = true
        }

        if (!isValidTime(state.endTime)) {
            _uiState.update { it.copy(endTimeError = "Formato inválido (HH:MM)") }
            hasError = true
        }

        if (hasError) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, error = null) }

            val request = CreateBookingRequest(
                startTime = "${date}T${state.startTime}:00",
                endTime = "${date}T${state.endTime}:00",
                professorId = state.professorId,
                type = state.type,
                title = state.title,
                description = state.description.ifBlank { null },
                roomId = roomId,
                repeat = state.repeat
            )

            bookingRepository.createBooking(request)
                .onSuccess {
                    _uiState.update { it.copy(isSubmitting = false, success = true) }
                    onSuccess()
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isSubmitting = false, error = error.message) }
                }
        }
    }

    private fun isValidTime(time: String): Boolean {
        val regex = Regex("^([01]?\\d|2[0-3]):([0-5]\\d)$")
        return regex.matches(time)
    }
}
