package com.example.praktam2_2417051011

import Model.Documents
import Model.DocumentsSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktam2_2417051011.ui.theme.PrakTam2_2417051011Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTam2_2417051011Theme {
                DaftarDocumentsScreen()
            }
        }
    }
}

@Composable
fun DaftarDocumentsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFE3F2FD))
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "ComVault",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Aplikasi Arsip Dokumen Jurusan Ilmu Komputer",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF546E7A)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            DocumentsSource.dummyDocs.forEach { documents ->
                DetailScreen(documents = documents)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun DetailScreen(documents: Documents) {
    var isFavorite by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFE0E0E0), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        IconButton(
            onClick = { isFavorite = !isFavorite },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite Icon",
                tint = if (isFavorite) Color.Red else Color.LightGray
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = documents.ImageRes),
                contentDescription = documents.jenis,
                modifier = Modifier
                    .size(80.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = documents.jenis,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF263238)
                )

                Text(
                    text = "Jumlah: ${documents.jumlah} File",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF78909C)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { },
                    modifier = Modifier.height(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5C6BC0))
                ) {
                    Text("Lihat Dokumen", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
fun DaftarDocumentsPreview() {
    PrakTam2_2417051011Theme {
        DaftarDocumentsScreen()
    }
}