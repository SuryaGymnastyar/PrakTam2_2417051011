package com.example.praktam2_2417051011

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.praktam2_2417051011.data.model.Documents
import com.example.praktam2_2417051011.ui.screen.Dashboard
import com.example.praktam2_2417051011.ui.screen.DetailDocuments
import com.example.praktam2_2417051011.ui.screen.LoginScreen
import com.example.praktam2_2417051011.ui.theme.PrakTam2_2417051011Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrakTam2_2417051011Theme {
                val context = applicationContext
                val sharedPreferences = remember {
                    context.getSharedPreferences("comvault_prefs", MODE_PRIVATE)
                }

                var isLoggedIn by remember {
                    mutableStateOf(sharedPreferences.getBoolean("is_logged_in", false))
                }

                val navController = rememberNavController()

                var globalDocs by remember { mutableStateOf<List<Documents>>(emptyList()) }

                if (!isLoggedIn) {
                    LoginScreen(
                        onLoginSuccess = {
                            isLoggedIn = true
                        }
                    )
                } else {
                    NavHost(
                        navController = navController,
                        startDestination = "dashboard"
                    ) {
                        composable("dashboard") {
                            Dashboard(
                                navController = navController,
                                onDocsLoaded = { loadedDocs ->
                                    globalDocs = loadedDocs
                                },
                                onLogout = {
                                    isLoggedIn = false
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
                                    documents = Documents(jenis = jenisParam ?: "Unknown", jumlah = 0, imageUrl = ""),
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