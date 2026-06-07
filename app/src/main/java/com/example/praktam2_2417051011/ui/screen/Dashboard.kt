package com.example.praktam2_2417051011.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.praktam2_2417051011.data.model.Documents
import com.example.praktam2_2417051011.data.repository.DocumentsRepository
import com.example.praktam2_2417051011.ui.theme.BlueHeadline

@Composable
fun Dashboard(
    navController: NavController,
    onDocsLoaded: (List<Documents>) -> Unit,
    onLogout: () -> Unit
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Beranda", "Dokumen", "Profile")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Search, Icons.Filled.Person)

    var documents by remember { mutableStateOf<List<Documents>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    val repository = remember { DocumentsRepository() }

    LaunchedEffect(Unit) {
        isLoading = true
        try {
            val response = repository.getDocs()
            if (response.isNotEmpty()) {
                documents = response
                onDocsLoaded(response)
                isLoading = false
            } else {
                isLoading = false
                isError = true
            }
        } catch (e: Exception) {
            android.util.Log.e("API_ERROR", "Gagal ngambil data documents: ${e.message}")
            isLoading = false
            isError = true
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BlueHeadline,
                            selectedTextColor = BlueHeadline,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> {
                    if (isLoading) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = BlueHeadline)
                        }
                    } else if (isError || documents.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Gagal Memuat Data",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Pastikan koneksi internet Anda menyala",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    } else {
                        DaftarDocument(navController = navController, docs = documents)
                    }
                }
                1 -> {
                    SearchScreen()
                }
                2 -> {
                    Profile(onLogoutSuccess = onLogout)
                }
            }
        }
    }
}