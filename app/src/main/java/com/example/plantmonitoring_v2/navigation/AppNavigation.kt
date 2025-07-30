package com.example.plantmonitoring_v2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.plantmonitoring_v2.screens.DashboardScreen
import com.example.plantmonitoring_v2.screens.LoginScreen
import com.example.plantmonitoring_v2.screens.PlantListScreen
import com.example.plantmonitoring_v2.screens.OnFieldScreen
import com.example.plantmonitoring_v2.screens.PlantDetailScreen
import com.example.plantmonitoring_v2.screens.ScheduledTask
import com.example.plantmonitoring_v2.screens.WateringSchedule

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object PlantList : Screen("plant_list")
    object OnField : Screen("on_field")
    object PlantDetail : Screen("plant_detail/{plantName}/{plantId}/{days}/{waterPerDay}/{wateringSchedules}")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                onPlantListClick = {
                    navController.navigate(Screen.PlantList.route)
                },
                onOnFieldClick = {
                    navController.navigate(Screen.OnField.route)
                }
            )
        }
        
        composable(Screen.PlantList.route) {
            PlantListScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.OnField.route) {
            OnFieldScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onPlantClick = { plant ->
                    val wateringSchedulesString = plant.wateringSchedules.joinToString("|") { "${it.time},${it.amount}" }
                    navController.navigate(
                        Screen.PlantDetail.route
                            .replace("{plantName}", plant.plantName)
                            .replace("{plantId}", plant.plantId)
                            .replace("{days}", plant.days.toString())
                            .replace("{waterPerDay}", plant.waterPerDay)
                            .replace("{wateringSchedules}", wateringSchedulesString)
                    )
                }
            )
        }
        
        composable(
            route = Screen.PlantDetail.route,
            arguments = listOf(
                navArgument("plantName") { type = NavType.StringType },
                navArgument("plantId") { type = NavType.StringType },
                navArgument("days") { type = NavType.IntType },
                navArgument("waterPerDay") { type = NavType.StringType },
                navArgument("wateringSchedules") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val plantName = backStackEntry.arguments?.getString("plantName") ?: ""
            val plantId = backStackEntry.arguments?.getString("plantId") ?: ""
            val days = backStackEntry.arguments?.getInt("days") ?: 0
            val waterPerDay = backStackEntry.arguments?.getString("waterPerDay") ?: ""
            val wateringSchedulesString = backStackEntry.arguments?.getString("wateringSchedules") ?: ""
            
            val wateringSchedules = wateringSchedulesString.split("|").filter { it.isNotEmpty() }.map { schedule ->
                val parts = schedule.split(",")
                if (parts.size == 2) {
                    WateringSchedule(parts[0], parts[1])
                } else {
                    WateringSchedule("", "")
                }
            }
            
            val plant = ScheduledTask(
                plantName = plantName,
                plantId = plantId,
                days = days,
                waterPerDay = waterPerDay,
                wateringSchedules = wateringSchedules
            )
            
            PlantDetailScreen(
                plant = plant,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
} 