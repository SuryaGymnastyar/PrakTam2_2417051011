package com.example.praktam2_2417051011.data.api

import com.example.praktam2_2417051011.data.model.Documents
import retrofit2.http.GET

interface ApiService {
    @GET("jenis_file.json")
    suspend fun getDocuments(): List<Documents>
}