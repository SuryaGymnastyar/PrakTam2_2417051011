package com.example.praktam2_2417051011.data.model

import java.lang.System.currentTimeMillis

data class LocalFile(
    val id: Int,
    val kodeMatkul: String,
    val namaFile: String,
    val jenisDokumen: String,
    val filePath: String?,
    val isFavorite: Boolean = false,
    val timestamp: Long = currentTimeMillis()
)