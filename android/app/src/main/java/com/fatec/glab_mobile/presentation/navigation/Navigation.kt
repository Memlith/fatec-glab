package com.fatec.glab_mobile.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fatec.glab_mobile.presentation.screens.nova_reserva.NovaReservaScreen
import com.fatec.glab_mobile.presentation.screens.reservas.ReservasScreen

sealed class Screen(val route: String) {
    object Reservas : Screen("reservas")
    object NovaReserva : Screen("nova_reserva/{date}/{room}") {
        fun createRoute(date: String, room: String): String = "nova_reserva/$date/$room"
    }
}

@Composable
fun GLabNavHost(
    navController: NavHostController,
    startDestination: String = Screen.Reservas.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Reservas.route) {
            ReservasScreen(
                onNavigateToNovaReserva = { date, room ->
                    navController.navigate(Screen.NovaReserva.createRoute(date, room))
                }
            )
        }

        composable(
            route = Screen.NovaReserva.route,
            arguments = listOf(
                navArgument("date") { type = NavType.StringType },
                navArgument("room") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val room = backStackEntry.arguments?.getString("room") ?: ""
            NovaReservaScreen(
                date = date,
                roomId = room,
                onNavigateBack = { navController.popBackStack() },
                onDateSelected = { newDate ->
                    navController.navigate(Screen.NovaReserva.createRoute(newDate, room)) {
                        popUpTo(Screen.NovaReserva.route) { inclusive = true }
                    }
                },
                onRoomSelected = { newRoom ->
                    navController.navigate(Screen.NovaReserva.createRoute(date, newRoom)) {
                        popUpTo(Screen.NovaReserva.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
