package com.example.praktam2_2417051011.data.api

import com.example.praktam2_2417051011.data.model.Documents
import retrofit2.http.GET

interface ApiService {
    //get meminta data dari http dan suspend fun untu corrutine, untuk mengambil data dokumen dari json.
    @GET("jenis_file.json")
    suspend fun getDocuments(): List<Documents>
}