package com.example.praktam2_2417051011.data.model

import com.google.gson.annotations.SerializedName

data class Matkul(
    @SerializedName("kode")
    val kode: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("semester")
    val semester: Int
)