package com.example.praktam2_2417051011.data.model

import com.google.gson.annotations.SerializedName

//data class modul 3

data class Documents (
    //serializedname ngasihtau kalo di jsn kan imageUrl tapi kita bisa make image_url
    @SerializedName("jenis")
    val jenis: String,
    @SerializedName("image_url")
    val imageUrl: String
)