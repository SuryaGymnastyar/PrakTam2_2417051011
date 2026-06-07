package com.example.praktam2_2417051011.ui.screen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.praktam2_2417051011.data.model.Documents
import com.example.praktam2_2417051011.data.model.LocalFile
import com.example.praktam2_2417051011.data.model.Matkul
import com.example.praktam2_2417051011.data.repository.MatkulRepository
import com.example.praktam2_2417051011.ui.theme.BlueHeadline
import com.example.praktam2_2417051011.ui.theme.CardSurface
import com.example.praktam2_2417051011.ui.theme.onPrimaryText
import com.example.praktam2_2417051011.ui.theme.onSecondaryText
import java.io.File
import java.io.FileOutputStream

@Composable
fun DetailDocuments(
    documents: Documents,
    navController: NavController
) {
    val context = LocalContext.current
    var selectedSemester by remember { mutableStateOf<Int?>(null) }
    var selectedMatkul by remember { mutableStateOf<Matkul?>(null) }

    var masterMatkulList by remember { mutableStateOf<List<Matkul>>(emptyList()) }
    var currentFileList by remember { mutableStateOf<List<LocalFile>>(emptyList()) }

    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf<LocalFile?>(null) }
    var showReadDialog by remember { mutableStateOf<LocalFile?>(null) }
    var showDeleteConfirmDialog by remember { mutableStateOf<LocalFile?>(null) }

    var inputFileName by remember { mutableStateOf("") }
    var editFileNameInput by remember { mutableStateOf("") }

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var actualSystemName by remember { mutableStateOf("") }

    val repository = remember { MatkulRepository() }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            val cursor = context.contentResolver.query(it, null, null, null, null)
            cursor?.use { c ->
                if (c.moveToFirst()) {
                    val nameIndex = c.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        actualSystemName = c.getString(nameIndex)
                        inputFileName = actualSystemName.substringBeforeLast(".")
                    }
                }
            }
            showAddDialog = true
        }
    }

    LaunchedEffect(Unit) {
        masterMatkulList = repository.getMatkul()
    }

    LaunchedEffect(selectedMatkul, showAddDialog, showEditDialog, showDeleteConfirmDialog) {
        selectedMatkul?.let { matkul ->
            currentFileList = repository.getFilesByMatkul(matkul.kode, documents.jenis)
        }
    }

    val filteredMatkulList = remember(selectedSemester, masterMatkulList) {
        masterMatkulList.filter { it.semester == selectedSemester }
    }

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            when {
                                selectedMatkul != null -> selectedMatkul = null
                                selectedSemester != null -> selectedSemester = null
                                else -> navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = BlueHeadline
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when {
                            selectedMatkul != null -> selectedMatkul!!.nama
                            selectedSemester != null -> "SEMESTER $selectedSemester"
                            else -> documents.jenis.uppercase()
                        },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = BlueHeadline,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedMatkul != null) {
                FloatingActionButton(
                    onClick = {
                        val mimeType = when (documents.jenis.lowercase()) {
                            "pdf" -> "application/pdf"
                            "word" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                            "excel" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                            "ppt" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation"
                            else -> "*/*"
                        }
                        filePickerLauncher.launch(mimeType)
                    },
                    containerColor = BlueHeadline,
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add File")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {
            when {
                selectedSemester == null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 24.dp)
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items((1..8).toList()) { sem ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp)
                                        .clickable { selectedSemester = sem },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = CardSurface),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "SEMESTER $sem",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = onPrimaryText
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                selectedMatkul == null -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(filteredMatkulList) { matkul ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedMatkul = matkul },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = CardSurface),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .background(BlueHeadline, shape = RoundedCornerShape(4.dp))
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = matkul.nama,
                                                style = MaterialTheme.typography.bodyLarge,
                                                fontWeight = FontWeight.SemiBold,
                                                color = onPrimaryText,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = matkul.kode,
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

                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        if (currentFileList.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Belum ada berkas terunggah",
                                    color = onSecondaryText,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(currentFileList, key = { it.id }) { file ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { showReadDialog = file },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = CardSurface),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(14.dp)
                                                    .background(Color.Gray, shape = RoundedCornerShape(2.dp))
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = "${file.namaFile}.${documents.jenis.lowercase()}",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = FontWeight.Medium,
                                                    color = onPrimaryText,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Text(
                                                    text = documents.jenis.uppercase(),
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = onSecondaryText
                                                )
                                            }
                                            IconButton(
                                                onClick = {
                                                    repository.toggleFavorite(file.id)
                                                    currentFileList = repository.getFilesByMatkul(selectedMatkul!!.kode, documents.jenis)
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = if (file.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                    contentDescription = "Favorite",
                                                    tint = Color.Red,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                            IconButton(
                                                onClick = {
                                                    editFileNameInput = file.namaFile
                                                    showEditDialog = file
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Edit,
                                                    contentDescription = "Edit File",
                                                    tint = BlueHeadline,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                            IconButton(
                                                onClick = {
                                                    showDeleteConfirmDialog = file
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Delete File",
                                                    tint = Color.Red,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (showAddDialog) {
                AlertDialog(
                    onDismissRequest = { showAddDialog = false },
                    title = { Text(text = "Tambah Berkas Baru", fontWeight = FontWeight.Bold) },
                    text = {
                        OutlinedTextField(
                            value = inputFileName,
                            onValueChange = { inputFileName = it },
                            label = { Text("Nama Berkas") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = BlueHeadline),
                            onClick = {
                                if (inputFileName.isNotBlank() && selectedMatkul != null) {
                                    var savedPath: String? = null
                                    selectedFileUri?.let { uri ->
                                        savedPath = saveToInternalStorage(context, uri, actualSystemName)
                                    }
                                    repository.createFile(selectedMatkul!!.kode, inputFileName, documents.jenis, savedPath)
                                }
                                showAddDialog = false
                            }
                        ) {
                            Text("Tambah", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAddDialog = false }) {
                            Text("Batal", color = BlueHeadline)
                        }
                    }
                )
            }

            if (showEditDialog != null) {
                AlertDialog(
                    onDismissRequest = { showEditDialog = null },
                    title = { Text(text = "Ubah Nama Berkas", fontWeight = FontWeight.Bold) },
                    text = {
                        OutlinedTextField(
                            value = editFileNameInput,
                            onValueChange = { editFileNameInput = it },
                            label = { Text("Nama Berkas Baru") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = BlueHeadline),
                            onClick = {
                                if (editFileNameInput.isNotBlank()) {
                                    repository.updateFileName(showEditDialog!!.id, editFileNameInput)
                                }
                                showEditDialog = null
                            }
                        ) {
                            Text("Simpan", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showEditDialog = null }) {
                            Text("Batal", color = BlueHeadline)
                        }
                    }
                )
            }

            if (showDeleteConfirmDialog != null) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmDialog = null },
                    title = { Text(text = "Hapus Berkas", fontWeight = FontWeight.Bold) },
                    text = {
                        Text(
                            text = "Yakin mau hapus file ini?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = onPrimaryText
                        )
                    },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            onClick = {
                                repository.deleteFile(showDeleteConfirmDialog!!.id)
                                showDeleteConfirmDialog = null
                            }
                        ) {
                            Text("Yakin", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteConfirmDialog = null }) {
                            Text("Batal", color = BlueHeadline)
                        }
                    }
                )
            }

            if (showReadDialog != null) {
                AlertDialog(
                    onDismissRequest = { showReadDialog = null },
                    title = { Text(text = "${showReadDialog!!.namaFile}.${documents.jenis.lowercase()}", fontWeight = FontWeight.Bold) },
                    text = {
                        Column {
                            Text(
                                text = "Lokasi File Terjaga:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = BlueHeadline
                            )
                            Text(
                                text = showReadDialog!!.filePath ?: "Tidak ada data path fisik",
                                style = MaterialTheme.typography.bodySmall,
                                color = onSecondaryText
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ini adalah isi konten dokumen asli untuk mata kuliah ${selectedMatkul?.nama}. Berkas biner Anda tersimpan dengan aman pada memori cache lokal aplikasi ComVault.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = onPrimaryText
                            )
                        }
                    },
                    confirmButton = {
                        Row {
                            TextButton(onClick = { showReadDialog = null }) {
                                Text("Tutup", color = BlueHeadline)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                colors = ButtonDefaults.buttonColors(containerColor = BlueHeadline),
                                onClick = {
                                    showReadDialog!!.filePath?.let { path ->
                                        openFile(context, path, documents.jenis)
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
}

private fun saveToInternalStorage(context: Context, uri: Uri, fileNameWithExtension: String): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val outputFile = File(context.filesDir, fileNameWithExtension)
        inputStream.use { input ->
            FileOutputStream(outputFile).use { output ->
                input.copyTo(output)
            }
        }
        outputFile.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun openFile(context: Context, filePath: String, jenisDokumen: String) {
    try {
        val file = File(filePath)
        if (!file.exists()) {
            android.widget.Toast.makeText(context, "Berkas fisik tidak ditemukan di cache", android.widget.Toast.LENGTH_SHORT).show()
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
        android.widget.Toast.makeText(context, "Gagal membuka berkas: ${e.localizedMessage}", android.widget.Toast.LENGTH_LONG).show()
    }
}