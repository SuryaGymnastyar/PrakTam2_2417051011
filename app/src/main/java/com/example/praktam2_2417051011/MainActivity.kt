package com.example.praktam2_2417051011

import Model.Documents
import Model.DocumentsSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun DaftarDocumentsScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFE3F2FD))
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

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

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            DocumentsSource.dummyDocs.forEach { documents ->
                DetailScreen(documents = documents)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun DetailScreen(documents: Documents){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Image(
                painter = painterResource(id = documents.ImageRes),
                contentDescription = documents.jenis,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = documents.jenis,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = documents.jumlah,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lihat Dokumen")
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 1900)
@Composable
fun DaftarDocumentsPreview() {
    PrakTam2_2417051011Theme {
        DaftarDocumentsScreen()
    }
}