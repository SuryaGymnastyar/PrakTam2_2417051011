package com.example.praktam2_2417051011.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.praktam2_2417051011.R
import com.example.praktam2_2417051011.ui.theme.BlueHeadline
import com.example.praktam2_2417051011.ui.theme.SearchFieldBackground
import com.example.praktam2_2417051011.ui.theme.onPrimaryText
import com.example.praktam2_2417051011.ui.theme.onSecondaryText
import androidx.core.content.edit

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
    var npmInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    var forgotNpmInput by remember { mutableStateOf("") }
    var forgotEmailInput by remember { mutableStateOf("") }

    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("comvault_prefs", Context.MODE_PRIVATE)
    }

    val dismissForgotDialog = {
        showForgotPasswordDialog = false
        forgotNpmInput = ""
        forgotEmailInput = ""
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo ComVault",
                modifier = Modifier.size(90.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Selamat Datang!",
                style = MaterialTheme.typography.headlineLarge,
                color = BlueHeadline,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Silakan login untuk mengakses berkas backup materi perkuliahan Anda",
                style = MaterialTheme.typography.bodyMedium,
                color = onSecondaryText,
                modifier = Modifier.padding(top = 4.dp, bottom = 32.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            CustomInputLabel(text = "Nomor Pokok Mahasiswa (NPM)")
            OutlinedTextField(
                value = npmInput,
                onValueChange = {
                    npmInput = it
                    isError = false
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Masukkan NPM Anda", color = onSecondaryText.copy(alpha = 0.5f)) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BlueHeadline) },
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
                value = passwordInput,
                onValueChange = {
                    passwordInput = it
                    isError = false
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Masukkan kata sandi Anda", color = onSecondaryText.copy(alpha = 0.5f)) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BlueHeadline) },
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null, tint = BlueHeadline)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BlueHeadline,
                    unfocusedBorderColor = SearchFieldBackground,
                    focusedTextColor = onPrimaryText,
                    unfocusedTextColor = onPrimaryText
                )
            )

            if (isError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "NPM atau Password salah!",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            TextButton(
                onClick = { showForgotPasswordDialog = true },
                modifier = Modifier.align(Alignment.End),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("Lupa Password?", color = BlueHeadline, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val registeredNpm = sharedPreferences.getString("registered_npm", "")
                    val registeredPassword = sharedPreferences.getString("registered_password", "")

                    if (npmInput.trim() == registeredNpm && passwordInput.trim() == registeredPassword && npmInput.isNotBlank()) {
                        sharedPreferences.edit { putBoolean("is_logged_in", true) }
                        onLoginSuccess()
                    } else {
                        isError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlueHeadline)
            ) {
                Text("Masuk", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Belum punya akun? ", color = onSecondaryText, style = MaterialTheme.typography.bodyMedium)
                TextButton(onClick = onNavigateToRegister, contentPadding = PaddingValues(0.dp)) {
                    Text("Daftar Sekarang", color = BlueHeadline, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    if (showForgotPasswordDialog) {
        AlertDialog(
            onDismissRequest = dismissForgotDialog,
            title = { Text("Pulihkan Password", fontWeight = FontWeight.Bold, color = BlueHeadline) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        "Masukkan NPM dan Email yang terdaftar untuk melihat password Anda.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = onSecondaryText
                    )
                    OutlinedTextField(
                        value = forgotNpmInput,
                        onValueChange = { forgotNpmInput = it },
                        placeholder = { Text("Masukkan NPM") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BlueHeadline, focusedTextColor = onPrimaryText, unfocusedTextColor = onPrimaryText)
                    )
                    OutlinedTextField(
                        value = forgotEmailInput,
                        onValueChange = { forgotEmailInput = it },
                        placeholder = { Text("Masukkan Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BlueHeadline, focusedTextColor = onPrimaryText, unfocusedTextColor = onPrimaryText)
                    )
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = BlueHeadline),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        val registeredNpm = sharedPreferences.getString("registered_npm", "")
                        val registeredEmail = sharedPreferences.getString("registered_email", "")
                        val registeredPassword = sharedPreferences.getString("registered_password", "")

                        if (forgotNpmInput.trim() == registeredNpm && forgotEmailInput.trim() == registeredEmail && forgotNpmInput.isNotBlank()) {
                            Toast.makeText(context, "Password Anda: $registeredPassword", Toast.LENGTH_LONG).show()
                            dismissForgotDialog()
                        } else {
                            Toast.makeText(context, "Data tidak cocok!", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Cek Password", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = dismissForgotDialog) {
                    Text("Batal", color = onSecondaryText)
                }
            }
        )
    }
}