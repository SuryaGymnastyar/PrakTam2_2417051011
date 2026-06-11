package com.example.praktam2_2417051011

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.praktam2_2417051011.data.model.Documents
import com.example.praktam2_2417051011.ui.screen.Dashboard
import com.example.praktam2_2417051011.ui.screen.DetailDocuments
import com.example.praktam2_2417051011.ui.screen.LoginScreen
import com.example.praktam2_2417051011.ui.screen.RegisterScreen
import com.example.praktam2_2417051011.ui.theme.PrakTam2_2417051011Theme
//Modul 8 Navcontroller, navhost
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrakTam2_2417051011Theme {
                val context = applicationContext
                val sharedPreferences = remember {
                    context.getSharedPreferences("comvault_prefs", MODE_PRIVATE)
                }

                val isLoggedInInitial = remember {
                    sharedPreferences.getBoolean("is_logged_in", false)
                }

                val navController = rememberNavController()
                var globalDocs by remember { mutableStateOf<List<Documents>>(emptyList()) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if (isLoggedInInitial) "dashboard" else "login"
                    ) {
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate("dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onNavigateToRegister = {
                                    navController.navigate("register")
                                }
                            )
                        }

                        composable("register") {
                            RegisterScreen(
                                onRegisterSuccess = {
                                    navController.navigate("login") {
                                        popUpTo("register") { inclusive = true }
                                    }
                                },
                                onNavigateToLogin = {
                                    navController.navigate("login") {
                                        popUpTo("register") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("dashboard") {
                            Dashboard(
                                navController = navController,
                                onDocsLoaded = { loadedDocs ->
                                    globalDocs = loadedDocs
                                },
                                onLogout = {
                                    navController.navigate("login") {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(
                            route = "detail/{jenis}",
                            arguments = listOf(navArgument("jenis") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val jenisParam = backStackEntry.arguments?.getString("jenis")
                            val currentDoc = globalDocs.find { it.jenis == jenisParam }

                            if (currentDoc != null) {
                                DetailDocuments(
                                    documents = currentDoc,
                                    navController = navController,
                                )
                            } else {
                                DetailDocuments(
                                    documents = Documents(jenis = jenisParam ?: "Unknown", imageUrl = ""),
                                    navController = navController,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}