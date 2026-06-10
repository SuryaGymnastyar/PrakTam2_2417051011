package com.example.praktam2_2417051011.ui.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.praktam2_2417051011.R
import com.example.praktam2_2417051011.data.model.Documents
import com.example.praktam2_2417051011.ui.theme.BlueHeadline
import com.example.praktam2_2417051011.ui.theme.CardSurface
import com.example.praktam2_2417051011.ui.theme.onPrimaryText
import com.example.praktam2_2417051011.ui.theme.onSecondaryText

@Composable
fun DaftarDocument(navController: NavController, docs: List<Documents>) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("comvault_prefs", Context.MODE_PRIVATE)
    }
    val loginNama = remember {
        sharedPreferences.getString("registered_nama", "User ComVault") ?: "User ComVault"
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueHeadline, RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Column {
                Text(
                    text = "Hi $loginNama!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Selamat datang di Comvault!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CardSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1.2f)) {
                            Text(
                                text = "Comvault",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = BlueHeadline
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Simpan backup dan arsip file materi anda dengan aman dan terstruktur.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = onSecondaryText
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(0.8f)
                                .height(80.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo Comvault",
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }
                }
            }

            item(span = { GridItemSpan(2) }) {
                Column {
                    Text(
                        text = "Jenis Dokumen",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = onPrimaryText,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(docs) { doc ->
                            Card(
                                modifier = Modifier
                                    .width(130.dp)
                                    .clickable { navController.navigate("detail/${doc.jenis}") },
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = doc.imageUrl,
                                        contentDescription = doc.jenis,
                                        placeholder = painterResource(id = R.drawable.word),
                                        error = painterResource(id = R.drawable.word),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = doc.jenis,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = onPrimaryText
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item(span = { GridItemSpan(2) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Semua Berkas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = onPrimaryText
                    )
                }
            }

            items(docs) { doc ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("detail/${doc.jenis}") },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CardSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        AsyncImage(
                            model = doc.imageUrl,
                            contentDescription = doc.jenis,
                            placeholder = painterResource(id = R.drawable.word),
                            error = painterResource(id = R.drawable.word),
                            modifier = Modifier.size(36.dp),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = doc.jenis,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = onPrimaryText
                        )
                    }
                }
            }

            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}