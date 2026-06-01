package com.example.praktam2_2417051011.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.praktam2_2417051011.data.model.LocalFile
import com.example.praktam2_2417051011.data.repository.MatkulRepository
import com.example.praktam2_2417051011.ui.theme.BlueHeadline
import com.example.praktam2_2417051011.ui.theme.CardSurface
import com.example.praktam2_2417051011.ui.theme.onPrimaryText
import com.example.praktam2_2417051011.ui.theme.onSecondaryText

@Composable
fun SearchScreen(
    navController: NavController
) {
    var query by remember { mutableStateOf("") }
    val allFiles = MatkulRepository.dummyFiles
    var showReadDialog by remember { mutableStateOf<LocalFile?>(null) }

    val filteredFiles = remember(query, allFiles) {
        if (query.isBlank()) {
            allFiles
        } else {
            allFiles.filter { it.namaFile.contains(query, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Pencarian",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = BlueHeadline,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = { Text("Cari nama file dokumen...", color = onSecondaryText) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = onSecondaryText
                )
            },
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = BlueHeadline,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredFiles.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = if (query.isBlank()) "Belum ada berkas terunggah di aplikasi" else "Tidak ada file yang cocok",
                    color = onSecondaryText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = if (query.isBlank()) "Semua Berkas Dokumen" else "Hasil Pencarian Berkas",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = BlueHeadline,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                items(filteredFiles, key = { it.id }) { file ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showReadDialog = file
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurface)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .background(Color.Gray, shape = RoundedCornerShape(2.dp))
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "${file.namaFile}.${file.jenisDokumen.lowercase()}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = onPrimaryText,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${file.jenisDokumen.uppercase()} • Kode Matkul: ${file.kodeMatkul}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = onSecondaryText
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showReadDialog != null) {
        AlertDialog(
            onDismissRequest = { showReadDialog = null },
            title = { Text(text = "${showReadDialog!!.namaFile}.${showReadDialog!!.jenisDokumen.lowercase()}", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    text = "Ini adalah isi konten dokumen dari mata kuliah dengan kode ${showReadDialog!!.kodeMatkul}. Berkas ini diakses langsung dari menu pencarian ComVault.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = onPrimaryText
                )
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = BlueHeadline),
                    onClick = { showReadDialog = null }
                ) {
                    Text("Tutup", color = Color.White)
                }
            }
        )
    }
}