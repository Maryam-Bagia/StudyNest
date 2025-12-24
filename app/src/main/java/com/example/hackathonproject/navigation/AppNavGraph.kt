package com.example.hackathonproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.hackathonproject.ui.auth.LoginScreen
import com.example.hackathonproject.ui.auth.SignUpScreen
import com.example.hackathonproject.ui.home.HomeScreen
import com.example.hackathonproject.ui.subject.SubjectHomeScreen
import com.example.hackathonproject.ui.subject.MaterialListScreen
import com.example.hackathonproject.viewmodel.AuthViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("signup") {
            SignUpScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("home") {
            HomeScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(
            route = "subject_home/{subjectId}/{subjectName}/{subjectCode}?isDarkMode={isDarkMode}",
            arguments = listOf(
                navArgument("subjectId") { type = NavType.StringType },
                navArgument("subjectName") { type = NavType.StringType },
                navArgument("subjectCode") { type = NavType.StringType },
                navArgument("isDarkMode") { type = NavType.BoolType; defaultValue = false }
            )
        ) { backStackEntry ->
            SubjectHomeScreen(
                navController = navController,
                authViewModel = authViewModel,
                subjectId = backStackEntry.arguments?.getString("subjectId"),
                subjectName = backStackEntry.arguments?.getString("subjectName"),
                subjectCode = backStackEntry.arguments?.getString("subjectCode"),
                isDarkMode = backStackEntry.arguments?.getBoolean("isDarkMode") ?: false
            )
        }
        composable(
            route = "material_list/{subjectId}/{subjectName}/{materialType}?isDarkMode={isDarkMode}",
            arguments = listOf(
                navArgument("subjectId") { type = NavType.StringType },
                navArgument("subjectName") { type = NavType.StringType },
                navArgument("materialType") { type = NavType.StringType },
                navArgument("isDarkMode") { type = NavType.BoolType; defaultValue = false }
            )
        ) { backStackEntry ->
            MaterialListScreen(
                navController = navController,
                subjectId = backStackEntry.arguments?.getString("subjectId"),
                subjectName = backStackEntry.arguments?.getString("subjectName"),
                materialTypeName = backStackEntry.arguments?.getString("materialType"),
                isDarkMode = backStackEntry.arguments?.getBoolean("isDarkMode") ?: false
            )
        }
    }
}
