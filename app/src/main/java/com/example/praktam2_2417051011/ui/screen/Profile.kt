package com.example.praktam2_2417051011.ui.screen

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import coil.compose.AsyncImage
import com.example.praktam2_2417051011.ui.theme.BlueHeadline
import com.example.praktam2_2417051011.ui.theme.CardSurface
import com.example.praktam2_2417051011.ui.theme.CategoryCardBackground
import com.example.praktam2_2417051011.ui.theme.SearchFieldBackground
import com.example.praktam2_2417051011.ui.theme.onPrimaryText
import com.example.praktam2_2417051011.ui.theme.onSecondaryText
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun Profile(onLogoutSuccess: () -> Unit) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val sharedPreferences = remember { context.getSharedPreferences("comvault_prefs", Context.MODE_PRIVATE) }

    var userNama by remember { mutableStateOf(sharedPreferences.getString("registered_nama", "User ComVault") ?: "") }
    val userNpm = remember { sharedPreferences.getString("registered_npm", "-") ?: "" }
    var userEmail by remember { mutableStateOf(sharedPreferences.getString("registered_email", "-") ?: "") }
    val userProdi = "Ilmu Komputer"
    var profileImagePath by remember { mutableStateOf(sharedPreferences.getString("profile_image_path", null)) }

    var showLogoutDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var editNamaInput by remember { mutableStateOf("") }
    var editEmailInput by remember { mutableStateOf("") }
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    val dismissEditDialog = { focusManager.clearFocus(); showEditDialog = false; editNamaInput = ""; editEmailInput = "" }
    val dismissLogoutDialog = { showLogoutDialog = false }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            saveImageToInternalStorage(context, it)?.let { savedPath ->
                profileImagePath = savedPath
                sharedPreferences.edit { putString("profile_image_path", savedPath) }
                Toast.makeText(context, "Foto profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val imageModel: Any = remember(profileImagePath, userNama) {
        if (!profileImagePath.isNullOrEmpty()) File(profileImagePath!!)
        else "https://ui-avatars.com/api/?name=${URLEncoder.encode(userNama, StandardCharsets.UTF_8.toString())}&background=E3F2FD&color=1565C0&size=128"
    }

    val customSelectionColors = TextSelectionColors(handleColor = Color.Transparent, backgroundColor = Color.Transparent)

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier.fillMaxWidth().background(BlueHeadline, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)).padding(top = 48.dp, bottom = 32.dp), contentAlignment = Alignment.Center) {
            IconButton(onClick = { editNamaInput = userNama; editEmailInput = userEmail; showEditDialog = true }, modifier = Modifier.align(Alignment.TopEnd).padding(end = 16.dp)) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Profil", tint = Color.White)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(model = imageModel, contentDescription = "Foto Profil", modifier = Modifier.size(100.dp).clip(CircleShape).clickable { galleryLauncher.launch("image/*") }, contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Ketuk foto untuk mengubah", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.7f))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = userNama, style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = "Prodi $userProdi", style = MaterialTheme.typography.bodyMedium, color = CategoryCardBackground)
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ProfileInfoItem(Icons.Default.Person, "NPM", userNpm)
            ProfileInfoItem(Icons.Default.Info, "Prodi", userProdi)
            ProfileInfoItem(Icons.Default.Email, "Email", userEmail)
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { showLogoutDialog = true }, modifier = Modifier.fillMaxWidth().height(54.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF5350))) {
                Text("Keluar Akun", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }

    if (showEditDialog) {
        CompositionLocalProvider(LocalTextSelectionColors provides customSelectionColors) {
            AlertDialog(
                onDismissRequest = dismissEditDialog,
                shape = RoundedCornerShape(28.dp),
                containerColor = MaterialTheme.colorScheme.surface,
                title = { Text("Ubah Profil", fontWeight = FontWeight.Bold, color = BlueHeadline) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column {
                            Text("Nama Lengkap", fontWeight = FontWeight.Bold, color = onPrimaryText, modifier = Modifier.padding(start = 4.dp, bottom = 6.dp))
                            OutlinedTextField(
                                value = editNamaInput, onValueChange = { editNamaInput = it },
                                placeholder = { Text("Masukkan Nama Lengkap", color = onSecondaryText.copy(alpha = 0.5f)) },
                                singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                                interactionSource = remember { MutableInteractionSource() },
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BlueHeadline, unfocusedBorderColor = SearchFieldBackground, focusedTextColor = onPrimaryText, unfocusedTextColor = onPrimaryText)
                            )
                        }
                        Column {
                            Text("Email", fontWeight = FontWeight.Bold, color = onPrimaryText, modifier = Modifier.padding(start = 4.dp, bottom = 6.dp))
                            OutlinedTextField(
                                value = editEmailInput, onValueChange = { editEmailInput = it },
                                placeholder = { Text("Masukkan Alamat Email", color = onSecondaryText.copy(alpha = 0.5f)) },
                                singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                                interactionSource = remember { MutableInteractionSource() },
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BlueHeadline, unfocusedBorderColor = SearchFieldBackground, focusedTextColor = onPrimaryText, unfocusedTextColor = onPrimaryText)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(colors = ButtonDefaults.buttonColors(containerColor = BlueHeadline), shape = RoundedCornerShape(10.dp), onClick = {
                        val trimmedNama = editNamaInput.trim()
                        val trimmedEmail = editEmailInput.trim()
                        if (trimmedNama.isBlank() || trimmedEmail.isBlank()) {
                            Toast.makeText(context, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                        }
                        else if (!emailRegex.matches(trimmedEmail)) {
                            Toast.makeText(context, "Format email tidak valid!", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            sharedPreferences.edit { putString("registered_nama", trimmedNama); putString("registered_email", trimmedEmail) }
                            userNama = trimmedNama; userEmail = trimmedEmail
                            Toast.makeText(context, "Profil Berhasil Diperbarui!", Toast.LENGTH_SHORT).show()
                            dismissEditDialog()
                        }
                    }) { Text("Simpan", color = Color.White, fontWeight = FontWeight.Bold) }
                },
                dismissButton = { TextButton(onClick = dismissEditDialog) { Text("Batal", color = onSecondaryText, fontWeight = FontWeight.Medium) } }
            )
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = dismissLogoutDialog,
            shape = RoundedCornerShape(28.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text("Konfirmasi Keluar", fontWeight = FontWeight.Bold, color = BlueHeadline) },
            text = { Text("Apakah Anda yakin ingin keluar dari akun ComVault?", style = MaterialTheme.typography.bodyMedium, color = onPrimaryText) },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF5350)), onClick = { dismissLogoutDialog(); sharedPreferences.edit { putBoolean("is_logged_in", false) }; onLogoutSuccess() }) {
                    Text("Keluar", color = Color.White)
                }
            },
            dismissButton = { TextButton(onClick = dismissLogoutDialog) { Text("Batal", color = BlueHeadline, fontWeight = FontWeight.SemiBold) } }
        )
    }
}

@Composable
fun ProfileInfoItem(icon: ImageVector, label: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardSurface), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = BlueHeadline)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = label, style = MaterialTheme.typography.labelMedium, color = onSecondaryText)
                Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = onPrimaryText)
            }
        }
    }
}

fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val internalFile = File(context.filesDir, "comvault_profile_pict.jpg")
        context.contentResolver.openInputStream(uri)?.use { input -> FileOutputStream(internalFile).use { output -> input.copyTo(output) } }
        internalFile.absolutePath
    } catch (e: Exception) { e.printStackTrace(); null }
}