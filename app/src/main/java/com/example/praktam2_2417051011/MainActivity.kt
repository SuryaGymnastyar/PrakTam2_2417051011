package com.example.praktam2_2417051011

import Model.Documents
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.praktam2_2417051011.network.RetrofitClient
import com.example.praktam2_2417051011.ui.theme.PrakTam2_2417051011Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTam2_2417051011Theme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavController) {
    var docsList by remember { mutableStateOf<List<Documents>>(emptyList()) }

    NavHost(
        navController = navController as NavHostController,
        startDestination = "home"
    ) {
        composable("home") {
            MainContainer(navController) { fetchedDocs ->
                docsList = fetchedDocs
            }
        }
        composable("detail/{jenis}") { backStackEntry ->
            val jenis = backStackEntry.arguments?.getString("jenis")
            val docs = docsList.find { it.jenis == jenis }

            if (docs != null) {
                DetailScreen(documents = docs, navController = navController, isFullScreen = true)
            }
        }
    }
}

@Composable
fun MainContainer(navController: NavController, onDocsLoaded: (List<Documents>) -> Unit) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Beranda", "Dokumen", "Profile")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Search, Icons.Filled.Person)

    var documents by remember { mutableStateOf<List<Documents>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.instance.getDocuments()
            documents = response
            onDocsLoaded(response)
            isLoading = false
            isError = false
        } catch (e: Exception) {
            isLoading = false
            isError = true
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.secondary,
                            selectedTextColor = MaterialTheme.colorScheme.secondary,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF1565C0))
                }
            } else if (isError || documents.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Gagal Memuat Data",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Pastikan koneksi internet Anda menyala",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                DaftarDocumentsScreen(navController = navController, docs = documents)
            }
        }
    }
}

@Composable
fun DaftarDocumentsScreen(navController: NavController, docs: List<Documents>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HeaderSection()
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Jenis Dokumen",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(docs) { doc ->
                        DocsRowItem(docs = doc, navController = navController)
                    }
                }
            }

            item {
                Text(
                    text = "List File Materi:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(docs) { doc ->
                DetailScreen(documents = doc, navController = navController, isFullScreen = false)
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFE3F2FD),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
            .padding(horizontal = 24.dp, vertical = 40.dp)
    ) {
        Text(text = "ComVault", style = MaterialTheme.typography.headlineLarge, color = Color(0xFF1565C0))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Selamat datang, Surya!", style = MaterialTheme.typography.titleMedium, color = Color(0xFF334155))
    }
}

@Composable
fun DocsRowItem(docs: Documents, navController: NavController) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { navController.navigate("detail/${docs.jenis}") },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = docs.imageUrl,
                contentDescription = docs.jenis,
                placeholder = painterResource(id = R.drawable.word),
                error = painterResource(id = R.drawable.word),
                modifier = Modifier.size(32.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = docs.jenis,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun DetailScreen(
    documents: Documents,
    navController: NavController,
    isFullScreen: Boolean = false
) {
    var isFavorite by remember { mutableStateOf(false) }
    var isFileLoading by remember { mutableStateOf(false) }
    var isFolderOpen by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        AsyncImage(
                            model = documents.imageUrl,
                            contentDescription = documents.jenis,
                            placeholder = painterResource(id = R.drawable.word),
                            error = painterResource(id = R.drawable.word),
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isFolderOpen) "Semester 4" else documents.jenis,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isFolderOpen) "Isi Folder" else if (isFullScreen) "Semester 1 - 6" else "${documents.jumlah} Folder",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }

                    IconButton(onClick = { isFavorite = !isFavorite }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isFavorite) Color.Red else Color.LightGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (isFullScreen) {
                    if (!isFolderOpen) {
                        Text(text = "Daftar Folder Semester:", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(bottom = 8.dp))
                        (1..4).forEach { sem ->
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { if (sem == 4 && documents.jenis == "Word") isFolderOpen = true },
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
                            ) {
                                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.List, null, tint = Color(0xFFFFC107))
                                    Spacer(Modifier.width(12.dp))
                                    Text("Semester $sem", modifier = Modifier.weight(1f), color = Color(0xFF334155))
                                }
                            }
                        }
                    } else {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable(enabled = !isFileLoading) {
                                coroutineScope.launch {
                                    isFileLoading = true
                                    delay(2000)
                                    snackbarHostState.showSnackbar("Membuka Modul 12 TAM...")
                                    isFileLoading = false
                                }
                            },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    AsyncImage(
                                        model = documents.imageUrl,
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Column(Modifier.weight(1f)) {
                                        Text("${documents.jenis} Modul 12.docx", fontWeight = FontWeight.Bold, color = Color(0xFF334155))
                                        Text("2.4 MB", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                    }
                                    if (isFileLoading) {
                                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { if (isFolderOpen) isFolderOpen = false else navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Kembali", color = MaterialTheme.colorScheme.onPrimary)
                    }
                } else {
                    Button(
                        onClick = { navController.navigate("detail/${documents.jenis}") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Buka Dokumen", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    PrakTam2_2417051011Theme {
        val navController = rememberNavController()
        val dummyList = listOf(
            Documents("Word", 1, "https://cdn-icons-png.flaticon.com/512/281/281760.png"),
            Documents("Excel", 2, "https://cdn-icons-png.flaticon.com/512/281/281761.png"),
            Documents("PowerPoint", 3, "https://cdn-icons-png.flaticon.com/512/281/281762.png"),
            Documents("PDF", 4, "https://cdn-icons-png.flaticon.com/512/337/337946.png")
        )
        DaftarDocumentsScreen(navController = navController, docs = dummyList)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailPreview() {
    PrakTam2_2417051011Theme {
        val navController = rememberNavController()
        val dummy = Documents("Word", 1, "https://cdn-icons-png.flaticon.com/512/281/281760.png")
        DetailScreen(documents = dummy, navController = navController, isFullScreen = true)
    }
}