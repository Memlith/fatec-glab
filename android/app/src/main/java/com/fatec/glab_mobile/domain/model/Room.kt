package com.fatec.glab_mobile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val id: String,
    val label: String,
    val dbId: String? = null,
    val x: Int = 0,
    val y: Int = 0,
    val width: Int = 0,
    val height: Int = 0
)

@Serializable
data class Building(
    val terreo: List<Room> = emptyList(),
    val primeiro: List<Room> = emptyList(),
    val segundo: List<Room> = emptyList(),
    val terceiro: List<Room> = emptyList()
)

@Serializable
data class RoomsData(
    val building1: Building = Building(),
    val building2: Building = Building(),
    val building3: Building = Building()
)

val FLOORS = listOf("terreo", "primeiro", "segundo", "terceiro")

val FLOOR_LABELS = mapOf(
    "terreo" to "Térreo",
    "primeiro" to "1º Andar",
    "segundo" to "2º Andar",
    "terceiro" to "3º Andar"
)

val BUILDING_LABELS = mapOf(
    "1" to "Bloco 1",
    "2" to "Bloco 2",
    "3" to "Bloco 3"
)
