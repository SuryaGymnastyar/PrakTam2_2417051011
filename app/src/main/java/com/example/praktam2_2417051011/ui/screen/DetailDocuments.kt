package com.example.praktam2_2417051011.ui.screen

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

@Composable
fun DetailDocuments(
    documents: Documents,
    navController: NavController
) {
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

    val repository = remember { MatkulRepository() }

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
                        inputFileName = ""
                        showAddDialog = true
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
                                    repository.createFile(selectedMatkul!!.kode, inputFileName, documents.jenis)
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
                        Text(
                            text = "Ini adalah isi konten dokumen tiruan untuk mata kuliah ${selectedMatkul?.nama}. Berkas ini tersimpan dengan aman pada penyimpanan lokal memori aplikasi ComVault.",
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
    }
}