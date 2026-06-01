package com.example.praktam2_2417051011.data.model

import java.util.UUID

data class LocalFile(
    val id: String = UUID.randomUUID().toString(),
    val kodeMatkul: String,
    val namaFile: String,
    val jenisDokumen: String,
    val isFavorite: Boolean = false
)