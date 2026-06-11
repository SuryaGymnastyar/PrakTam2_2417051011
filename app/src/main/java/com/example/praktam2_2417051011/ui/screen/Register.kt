package com.example.praktam2_2417051011.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.example.praktam2_2417051011.ui.theme.BlueHeadline
import com.example.praktam2_2417051011.ui.theme.SearchFieldBackground
import com.example.praktam2_2417051011.ui.theme.onPrimaryText
import com.example.praktam2_2417051011.ui.theme.onSecondaryText
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onNavigateToLogin: () -> Unit) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("comvault_prefs", Context.MODE_PRIVATE)
    }

    var npm by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Buat Akun Baru",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = BlueHeadline
        )
        Text(
            text = "Daftarkan diri Anda untuk menyimpan arsip materi di ComVault",
            style = MaterialTheme.typography.bodyMedium,
            color = onSecondaryText,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        CustomInputLabel(text = "Nama Lengkap")
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            placeholder = { Text("Contoh: M. Surya Gymnastyar", color = onSecondaryText.copy(alpha = 0.5f)) },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BlueHeadline) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlueHeadline,
                unfocusedBorderColor = SearchFieldBackground,
                focusedTextColor = onPrimaryText,
                unfocusedTextColor = onPrimaryText
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomInputLabel(text = "NPM")
        OutlinedTextField(
            value = npm,
            onValueChange = { npm = it },
            placeholder = { Text("Contoh: 2417051011", color = onSecondaryText.copy(alpha = 0.5f)) },
            leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = BlueHeadline) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlueHeadline,
                unfocusedBorderColor = SearchFieldBackground,
                focusedTextColor = onPrimaryText,
                unfocusedTextColor = onPrimaryText
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomInputLabel(text = "Alamat Email")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Contoh: kalv@gmail.com", color = onSecondaryText.copy(alpha = 0.5f)) },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BlueHeadline) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlueHeadline,
                unfocusedBorderColor = SearchFieldBackground,
                focusedTextColor = onPrimaryText,
                unfocusedTextColor = onPrimaryText
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomInputLabel(text = "Kata Sandi")
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Minimal 6 karakter", color = onSecondaryText.copy(alpha = 0.5f)) },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BlueHeadline) },
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null, tint = BlueHeadline)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            visualTransformation = if (passwordVisible) androidx.compose.ui.text.input.VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlueHeadline,
                unfocusedBorderColor = SearchFieldBackground,
                focusedTextColor = onPrimaryText,
                unfocusedTextColor = onPrimaryText
            )
        )

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = {
                if (npm.isBlank() || nama.isBlank() || email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show()
                } else if (npm.length != 10 || !npm.all { it.isDigit() }) {
                    Toast.makeText(context, "NPM harus 10 angka!", Toast.LENGTH_SHORT).show()
                } else if (!email.matches(emailRegex)) {
                    Toast.makeText(context, "Format email tidak valid (contoh: user@gmail.com)!", Toast.LENGTH_SHORT).show()
                } else if (password.trim().length < 6) {
                    Toast.makeText(context, "Kata sandi minimal harus 6 karakter!", Toast.LENGTH_SHORT).show()
                } else {
                    sharedPreferences.edit {
                        putString("registered_npm", npm.trim())
                        putString("registered_nama", nama.trim())
                        putString("registered_email", email.trim())
                        putString("registered_prodi", "Ilmu Komputer")
                        putString("registered_password", password.trim())
                    }
                    Toast.makeText(context, "Akun Berhasil Dibuat!", Toast.LENGTH_SHORT).show()
                    onRegisterSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BlueHeadline)
        ) {
            Text("Daftar Akun", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Sudah punya akun? ", color = onSecondaryText, style = MaterialTheme.typography.bodyMedium)
            TextButton(onClick = onNavigateToLogin, contentPadding = PaddingValues(0.dp)) {
                Text("Login Sekarang", color = BlueHeadline, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CustomInputLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        color = onPrimaryText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, bottom = 6.dp)
    )
}