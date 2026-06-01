package com.example.praktam2_2417051011.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.praktam2_2417051011.ui.theme.BlueHeadline
import com.example.praktam2_2417051011.ui.theme.CardSurface
import com.example.praktam2_2417051011.ui.theme.onSecondaryText
import androidx.core.content.edit

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var npmInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("comvault_prefs", Context.MODE_PRIVATE)
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
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ComVault",
                style = MaterialTheme.typography.headlineLarge,
                color = BlueHeadline,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Amankan berkas praktikummu di sini",
                style = MaterialTheme.typography.bodyMedium,
                color = onSecondaryText
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = npmInput,
                onValueChange = {
                    npmInput = it
                    isError = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("NPM") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BlueHeadline) },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BlueHeadline,
                    unfocusedContainerColor = CardSurface,
                    focusedContainerColor = CardSurface
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = passwordInput,
                onValueChange = {
                    passwordInput = it
                    isError = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BlueHeadline) },
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BlueHeadline,
                    unfocusedContainerColor = CardSurface,
                    focusedContainerColor = CardSurface
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

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (npmInput == "2417051011" && passwordInput == "010706") {
                        sharedPreferences.edit { putBoolean("is_logged_in", true) }
                        onLoginSuccess()
                    } else {
                        isError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlueHeadline)
            ) {
                Text(
                    text = "Masuk",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}