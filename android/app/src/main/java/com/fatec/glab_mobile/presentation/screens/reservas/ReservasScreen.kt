package com.fatec.glab_mobile.presentation.screens.reservas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Arrangement as Arr
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Room
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material3.rememberDatePickerState
import androidx.hilt.navigation.compose.hiltViewModel
import com.fatec.glab_mobile.domain.model.BUILDING_LABELS
import com.fatec.glab_mobile.domain.model.Booking
import com.fatec.glab_mobile.domain.model.FLOOR_LABELS
import com.fatec.glab_mobile.domain.model.Room
import com.fatec.glab_mobile.presentation.components.SalaMap
import com.fatec.glab_mobile.presentation.theme.getCourseColor
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val HOUR_HEIGHT = 60
private const val START_HOUR = 7
private const val TOTAL_HOURS = 24 - START_HOUR + 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservasScreen(
    onNavigateToNovaReserva: (String, String) -> Unit,
    viewModel: ReservasViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showRoomSelector by remember { mutableStateOf(false) }
    var showBookingDetail by remember { mutableStateOf<Booking?>(null) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reservas", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val dateStr = uiState.selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                    onNavigateToNovaReserva(dateStr, uiState.selectedRoom.id)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nova Reserva")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DateSelectorCard(
                    date = uiState.selectedDate,
                    formattedDate = viewModel.getFormattedDate(),
                    onDateSelected = { viewModel.selectDate(it) },
                    modifier = Modifier.weight(1f)
                )

                RoomSelectorCard(
                    room = uiState.selectedRoom,
                    onClick = { showRoomSelector = true },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            PullToRefreshBox(
                isRefreshing = uiState.isLoading,
                onRefresh = { viewModel.loadBookings() },
                state = pullToRefreshState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = viewModel.getFormattedDate(),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "${uiState.bookings.size} ${if (uiState.bookings.size == 1) "reserva" else "reservas"}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        if (uiState.bookings.isEmpty() && !uiState.isLoading && uiState.error == null) {
                            EmptyStateView(
                                roomName = uiState.selectedRoom.label,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else if (uiState.error != null) {
                            ErrorStateView(
                                message = uiState.error!!,
                                onRetry = { viewModel.loadBookings() },
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            DailyScheduleTimeline(
                                bookings = uiState.bookings,
                                selectedDate = uiState.selectedDate,
                                onBookingClick = { showBookingDetail = it },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }

    if (showRoomSelector) {
        ModalBottomSheet(
            onDismissRequest = { showRoomSelector = false },
            sheetState = sheetState
        ) {
            RoomSelectorContent(
                buildings = listOf("1", "2", "3"),
                selectedBuilding = uiState.selectedBuilding,
                selectedFloor = uiState.selectedFloor,
                selectedRoom = uiState.selectedRoom,
                availableFloors = viewModel.getAvailableFloors(),
                rooms = viewModel.getCurrentRooms(),
                onBuildingSelect = { viewModel.selectBuilding(it) },
                onFloorSelect = { viewModel.selectFloor(it) },
                onRoomSelect = { room ->
                    viewModel.selectRoom(room)
                    scope.launch {
                        sheetState.hide()
                        showRoomSelector = false
                    }
                },
                onRoomInfo = { room ->
                    viewModel.selectRoom(room)
                }
            )
        }
    }

    var showEditDialog by remember { mutableStateOf<Booking?>(null) }
    var showDeleteDialog by remember { mutableStateOf<Booking?>(null) }

    showBookingDetail?.let { booking ->
        ModalBottomSheet(
            onDismissRequest = { showBookingDetail = null },
            sheetState = sheetState
        ) {
            BookingDetailContent(
                booking = booking,
                onDismiss = { showBookingDetail = null },
                onEdit = { showEditDialog = it },
                onDelete = { showDeleteDialog = it }
            )
        }
    }

    showEditDialog?.let { booking ->
        EditBookingDialog(
            booking = booking,
            professors = uiState.professors,
            onDismiss = { showEditDialog = null },
            onSave = { title, description, type, professorId, roomId, startTime, endTime, repeat ->
                viewModel.updateBooking(
                    bookingId = booking.id,
                    title = title,
                    description = description,
                    type = type,
                    professorId = professorId,
                    roomId = roomId,
                    startTime = startTime,
                    endTime = endTime,
                    repeat = repeat,
                    date = uiState.selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    onSuccess = {
                        showEditDialog = null
                        showBookingDetail = null
                    },
                    onError = { /* Error handled by ViewModel */ }
                )
            }
        )
    }

    showDeleteDialog?.let { booking ->
        DeleteBookingDialog(
            booking = booking,
            onDismiss = { showDeleteDialog = null },
            onConfirm = {
                viewModel.deleteBooking(
                    bookingId = booking.id,
                    onSuccess = {
                        showDeleteDialog = null
                        showBookingDetail = null
                    },
                    onError = { /* Error handled by ViewModel */ }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectorCard(
    date: LocalDate,
    formattedDate: String,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedLocalDate by remember { mutableStateOf(date) }

    Card(
        modifier = modifier.clickable { showDatePicker = true },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = "Selecionar data",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = formattedDate.split(",").firstOrNull() ?: "",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = formattedDate.split(",").getOrNull(1)?.trim() ?: "",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedLocalDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val newDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                            selectedLocalDate = newDate
                            onDateSelected(newDate)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = true
            )
        }
    }
}

@Composable
fun RoomSelectorCard(
    room: Room,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Room,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = room.label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Selecionar sala",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun DailyScheduleTimeline(
    bookings: List<Booking>,
    selectedDate: LocalDate,
    onBookingClick: (Booking) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val totalHeightDp = (TOTAL_HOURS * HOUR_HEIGHT).dp

    val currentTime = LocalTime.now()
    val currentMinuteOffset = if (selectedDate == LocalDate.now()) {
        val minutesSinceStart = (currentTime.hour - START_HOUR) * 60 + currentTime.minute
        if (minutesSinceStart in 0..(TOTAL_HOURS * 60)) minutesSinceStart.toFloat() / 60f else null
    } else null

    Row(modifier = modifier.verticalScroll(scrollState)) {
        Column(
            modifier = Modifier
                .width(48.dp)
                .height(totalHeightDp)
        ) {
            for (i in 0 until TOTAL_HOURS) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(HOUR_HEIGHT.dp)
                ) {
                    Text(
                        text = "${(START_HOUR + i).toString().padStart(2, '0')}:00",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .width(1.dp)
                .height(totalHeightDp)
                .background(MaterialTheme.colorScheme.outlineVariant)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(totalHeightDp)
        ) {
            for (i in 0 until TOTAL_HOURS) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(HOUR_HEIGHT.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                            .align(Alignment.TopStart)
                    )
                }
            }

            bookings.forEach { booking ->
                val startTime = parseTime(booking.startTime)
                val endTime = parseTime(booking.endTime)
                if (startTime != null && endTime != null) {
                    val pos = calculateVerticalPosition(startTime, endTime, START_HOUR)
                    TimelineBookingCard(
                        booking = booking,
                        onClick = { onBookingClick(booking) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 16.dp)
                            .padding(top = pos.top.dp)
                            .height(pos.height.dp.coerceAtLeast(40.dp))
                    )
                }
            }

            currentMinuteOffset?.let { offset ->
                val topOffset = (offset * HOUR_HEIGHT).dp
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(totalHeightDp)
                        .align(Alignment.TopStart)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 16.dp)
                            .padding(top = topOffset)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.Red)
                        )
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.Red, CircleShape)
                                .align(Alignment.CenterStart)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimelineBookingCard(
    booking: Booking,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val courseColor = getCourseColor(booking.type)

    Card(
        modifier = modifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(2.dp, courseColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = booking.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .background(courseColor, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = booking.type.uppercase().take(3),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    Icons.Default.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${formatTime(booking.startTime)} - ${formatTime(booking.endTime)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = booking.professorName ?: "Professor não informado",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun RoomSelectorContent(
    buildings: List<String>,
    selectedBuilding: String,
    selectedFloor: String,
    selectedRoom: Room,
    availableFloors: List<String>,
    rooms: List<Room>,
    onBuildingSelect: (String) -> Unit,
    onFloorSelect: (String) -> Unit,
    onRoomSelect: (Room) -> Unit,
    onRoomInfo: (Room) -> Unit
) {
    var buildingIndex by remember { mutableIntStateOf(buildings.indexOf(selectedBuilding).coerceAtLeast(0)) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Selecionar Sala",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${FLOOR_LABELS[selectedFloor] ?: selectedFloor} - ${BUILDING_LABELS[selectedBuilding] ?: selectedBuilding}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            ScrollableTabRow(
                selectedTabIndex = buildingIndex,
                edgePadding = 0.dp
            ) {
                buildings.forEachIndexed { index, building ->
                    Tab(
                        selected = buildingIndex == index,
                        onClick = {
                            buildingIndex = index
                            onBuildingSelect(building)
                        },
                        text = { Text(BUILDING_LABELS[building] ?: building) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (availableFloors.size > 1) {
                ScrollableTabRow(
                    selectedTabIndex = availableFloors.indexOf(selectedFloor).coerceAtLeast(0),
                    edgePadding = 0.dp
                ) {
                    availableFloors.forEachIndexed { index, floor ->
                        Tab(
                            selected = selectedFloor == floor,
                            onClick = { onFloorSelect(floor) },
                            text = { Text(FLOOR_LABELS[floor] ?: floor) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SalaMap(
            rooms = rooms,
            selectedRoomId = selectedRoom.id,
            building = selectedBuilding.toIntOrNull() ?: 1,
            onRoomSelected = onRoomSelect,
            onRoomInfo = onRoomInfo,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RoomCard(
    room: Room,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = room.label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun BookingDetailContent(
    booking: Booking,
    onDismiss: () -> Unit,
    onEdit: (Booking) -> Unit,
    onDelete: (Booking) -> Unit,
    modifier: Modifier = Modifier
) {
    val courseColor = getCourseColor(booking.type)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = booking.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .background(courseColor, RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = if (booking.type == "agendamento") "Agendamento" else booking.type.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                Icons.Default.AccessTime,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${formatTime(booking.startTime)} - ${formatTime(booking.endTime)}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (booking.description.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = booking.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (booking.repeat) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Repete semanalmente",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { onEdit(booking) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Editar")
            }
            Button(
                onClick = { onDelete(booking) },
                modifier = Modifier.weight(1f),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Excluir")
            }
        }
    }
}

@Composable
fun ErrorStateView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Text(
                text = "Ops! Algo deu errado",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Button(onClick = onRetry) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text("Tentar novamente")
            }
        }
    }
}

@Composable
fun EmptyStateView(
    roomName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.BookOnline,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
            Text(
                text = "Nenhuma reserva",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Não há reservas para $roomName\nneste dia",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Toque no + para criar uma reserva",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}

private fun parseTime(isoString: String): LocalTime? {
    return try {
        val timePart = isoString.split("T").getOrNull(1)?.substring(0, 5) ?: return null
        LocalTime.parse(timePart, DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: Exception) {
        null
    }
}

private fun formatTime(isoString: String): String {
    return try {
        isoString.split("T").getOrNull(1)?.substring(0, 5) ?: ""
    } catch (e: Exception) {
        ""
    }
}

private data class VerticalPosition(val top: Float, val height: Float)

private fun calculateVerticalPosition(
    startTime: LocalTime,
    endTime: LocalTime,
    startHour: Int
): VerticalPosition {
    val startMinutes = startTime.hour * 60 + startTime.minute
    val endMinutes = endTime.hour * 60 + endTime.minute
    val baseMinutes = startHour * 60

    val top = ((startMinutes - baseMinutes).toFloat() / 60f) * HOUR_HEIGHT
    val height = ((endMinutes - startMinutes).toFloat() / 60f) * HOUR_HEIGHT

    return VerticalPosition(top, height)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBookingDialog(
    booking: Booking,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Excluir Reserva") },
        text = { Text("Tem certeza que deseja excluir a reserva \"${booking.title}\"?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Excluir", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBookingDialog(
    booking: Booking,
    professors: List<com.fatec.glab_mobile.domain.model.Professor>,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String, String, String, Boolean) -> Unit
) {
    var title by remember { mutableStateOf(booking.title) }
    var description by remember { mutableStateOf(booking.description) }
    var type by remember { mutableStateOf(booking.type) }
    var professorId by remember { mutableStateOf(booking.professorId) }
    var roomId by remember { mutableStateOf(booking.roomId) }
    var repeat by remember { mutableStateOf(booking.repeat) }
    
    val startTimeValue = remember {
        booking.startTime.split("T").getOrNull(1)?.substring(0, 5) ?: "08:00"
    }
    val endTimeValue = remember {
        booking.endTime.split("T").getOrNull(1)?.substring(0, 5) ?: "10:00"
    }
    
    var startTime by remember { mutableStateOf(startTimeValue) }
    var endTime by remember { mutableStateOf(endTimeValue) }

    var typeExpanded by remember { mutableStateOf(false) }
    var professorExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Reserva", fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                ExposedDropdownMenuBox(
                    expanded = typeExpanded,
                    onExpandedChange = { typeExpanded = it }
                ) {
                    OutlinedTextField(
                        value = COURSE_TYPE_LABELS[type] ?: type,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = typeExpanded,
                        onDismissRequest = { typeExpanded = false }
                    ) {
                        COURSE_TYPE_LABELS.forEach { (value, label) ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    type = value
                                    typeExpanded = false
                                }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = professorExpanded,
                    onExpandedChange = { professorExpanded = it }
                ) {
                    OutlinedTextField(
                        value = professors.find { it.id == professorId }?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Professor") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = professorExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = professorExpanded,
                        onDismissRequest = { professorExpanded = false }
                    ) {
                        professors.forEach { professor ->
                            DropdownMenuItem(
                                text = { Text(professor.name) },
                                onClick = {
                                    professorId = professor.id
                                    professorExpanded = false
                                }
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TimePickerField(
                        label = "Início",
                        time = startTime,
                        onTimeSelected = { startTime = it },
                        modifier = Modifier.weight(1f)
                    )

                    TimePickerField(
                        label = "Término",
                        time = endTime,
                        onTimeSelected = { endTime = it },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = repeat,
                        onCheckedChange = { repeat = it }
                    )
                    Text("Repete semanalmente")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(title, description, type, professorId, roomId, startTime, endTime, repeat)
                },
                enabled = title.length >= 2 && startTime.length == 5 && endTime.length == 5
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

private val COURSE_TYPE_LABELS = mapOf(
    "agendamento" to "Agendamento",
    "DSM" to "DSM",
    "COMEX" to "COMEX",
    "REDES" to "REDES",
    "ADS" to "ADS",
    "GESTAO-EMP-V" to "Gestão Empresarial Vespertino",
    "GESTAO-EMP-N" to "Gestão Empresarial Noturno",
    "GESTAO-SERVICOS" to "Gestão de Serviços",
    "LOG-AERO" to "Logística Aeroportuária"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(
    label: String,
    time: String,
    onTimeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showTimePicker by remember { mutableStateOf(false) }

    val currentTime = try {
        if (time.isNotBlank()) {
            val parts = time.split(":")
            LocalTime.of(parts[0].toInt(), parts.getOrNull(1)?.toInt() ?: 0)
        } else null
    } catch (e: Exception) {
        null
    }

    @Suppress("DEPRECATION")
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime?.hour ?: 8,
        initialMinute = currentTime?.minute ?: 0
    )

    Card(
        modifier = modifier.clickable { showTimePicker = true },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = time.ifBlank { "--:--" },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

    if (showTimePicker) {
        Dialog(onDismissRequest = { showTimePicker = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selecione o horário",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        TimePicker(state = timePickerState)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showTimePicker = false }) {
                            Text("Cancelar")
                        }
                        TextButton(
                            onClick = {
                                val formattedTime = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                                onTimeSelected(formattedTime)
                                showTimePicker = false
                            }
                        ) {
                            Text("Confirmar")
                        }
                    }
                }
            }
        }
    }
}
