package com.example.hackathonproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.hackathonproject.navigation.AppNavGraph
import com.example.hackathonproject.ui.theme.HackathonProjectTheme
import com.example.hackathonproject.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HackathonProjectTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                AppNavGraph(navController = navController, authViewModel = authViewModel)
            }
        }
    }
}
