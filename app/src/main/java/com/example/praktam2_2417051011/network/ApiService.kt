package com.example.praktam2_2417051011.network

import Model.Documents
import retrofit2.http.GET

interface ApiService {
    @GET("jenis_file.json")
    suspend fun getDocuments(): List<Documents>
}