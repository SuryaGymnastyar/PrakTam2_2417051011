package com.example.praktam2_2417051011

import Model.DocumentsSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktam2_2417051011.ui.theme.PrakTam2_2417051011Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTam2_2417051011Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val allDocs = DocumentsSource.dummyDocs

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        allDocs.forEach { doc ->
                       Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = doc.ImageRes),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp).padding(end = 12.dp)
                )

                Column {
                    Text(text = "Tipe File: ${doc.jenis}")
                    Text(text = "Jumlah File: ${doc.jumlah}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrakTam2_2417051011Theme {
        Greeting()
    }
}