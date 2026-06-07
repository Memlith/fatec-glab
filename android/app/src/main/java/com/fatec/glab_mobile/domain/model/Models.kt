package com.fatec.glab_mobile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Booking(
    val id: String = "",
    val createdAt: String? = null,
    val startTime: String,
    val endTime: String,
    val repeat: Boolean = false,
    val type: String,
    val title: String,
    val description: String = "",
    val professorId: String,
    val roomId: String,
    val professorName: String? = null
)

@Serializable
data class Professor(
    val id: String,
    val name: String,
    val email: String
)

@Serializable
data class Classroom(
    val id: Int? = null,
    val name: String,
    val capacity: String,
    val equipmentsId: List<String> = emptyList(),
    val softwaresId: List<String> = emptyList()
)

@Serializable
data class Equipment(
    val id: String? = null,
    val name: String
)

@Serializable
data class Software(
    val id: String? = null,
    val name: String
)

@Serializable
data class User(
    val id: Int? = null,
    val name: String,
    val email: String
)

@Serializable
data class CreateBookingRequest(
    val startTime: String,
    val endTime: String,
    val professorId: String,
    val type: String,
    val title: String,
    val description: String?,
    val roomId: String,
    val repeat: Boolean
)
