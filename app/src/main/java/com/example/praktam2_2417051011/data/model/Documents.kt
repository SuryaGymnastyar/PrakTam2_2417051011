package com.example.praktam2_2417051011.data.model

import com.google.gson.annotations.SerializedName

data class Documents (
    @SerializedName("jenis")
    val jenis: String,
    @SerializedName("image_url")
    val imageUrl: String
)