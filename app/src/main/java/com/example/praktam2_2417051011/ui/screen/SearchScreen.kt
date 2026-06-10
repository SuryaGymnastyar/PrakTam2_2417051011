package com.example.praktam2_2417051011.ui.screen

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.praktam2_2417051011.data.model.LocalFile
import com.example.praktam2_2417051011.data.repository.MatkulRepository
import com.example.praktam2_2417051011.ui.theme.BlueHeadline
import com.example.praktam2_2417051011.ui.theme.CardSurface
import com.example.praktam2_2417051011.ui.theme.onPrimaryText
import com.example.praktam2_2417051011.ui.theme.onSecondaryText
import java.io.File

@Composable
fun SearchScreen() {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var showReadDialog by remember { mutableStateOf<LocalFile?>(null) }

    val dialogModifier = Modifier
        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(28.dp))

    val repository = remember { MatkulRepository() }

    val allFiles = remember(searchQuery) {
        repository.searchFiles(searchQuery)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueHeadline, RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Column {
                Text(
                    text = "Cari Berkas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Masukkan nama berkas...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = BlueHeadline) },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlueHeadline,
                        unfocusedBorderColor = BlueHeadline,
                        focusedContainerColor = CardSurface,
                        unfocusedContainerColor = CardSurface
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (allFiles.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (searchQuery.isEmpty()) "Belum ada berkas terunggah" else "Berkas tidak ditemukan",
                    color = onSecondaryText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(allFiles, key = { it.id }) { file ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showReadDialog = file },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${file.namaFile}.${file.jenisDokumen.lowercase()}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = onPrimaryText,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = file.jenisDokumen.uppercase(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = onSecondaryText
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showReadDialog != null) {
            AlertDialog(
                modifier = dialogModifier,
                onDismissRequest = { showReadDialog = null },
                title = { Text(text = "${showReadDialog!!.namaFile}.${showReadDialog!!.jenisDokumen.lowercase()}", fontWeight = FontWeight.Bold) },
                text = {
                    Text(
                        text = "Apakah Anda ingin membuka berkas ini sekarang?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = onPrimaryText
                    )
                },
                confirmButton = {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { showReadDialog = null }) {
                            Text("Batal", color = BlueHeadline, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = BlueHeadline),
                            onClick = {
                                showReadDialog!!.filePath?.let { path ->
                                    openFile(context, path, showReadDialog!!.jenisDokumen)
                                }
                                showReadDialog = null
                            }
                        ) {
                            Text("Buka Berkas", color = Color.White)
                        }
                    }
                }
            )
        }
    }
}

private fun openFile(context: Context, filePath: String, jenisDokumen: String) {
    try {
        val file = File(filePath)
        if (!file.exists()) {
            android.widget.Toast.makeText(context, "Berkas fisik tidak ditemukan", android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        val uri = androidx.core.content.FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val mimeType = when (jenisDokumen.lowercase()) {
            "pdf" -> "application/pdf"
            "word" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            "excel" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            "ppt" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation"
            else -> "*/*"
        }

        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val chooserIntent = android.content.Intent.createChooser(intent, "Buka berkas menggunakan:").apply {
            addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(chooserIntent)
    } catch (e: Exception) {
        e.printStackTrace()
        android.widget.Toast.makeText(context, "Gagal membuka berkas", android.widget.Toast.LENGTH_LONG).show()
    }
}