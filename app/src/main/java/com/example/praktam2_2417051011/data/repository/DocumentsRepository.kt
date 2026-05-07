package com.example.praktam2_2417051011.data.repository

import com.example.praktam2_2417051011.data.model.Documents
import com.example.praktam2_2417051011.data.api.RetrofitClient

class DocumentsRepository {
    suspend fun getDocs(): List<Documents> {
        return try {
            RetrofitClient.instance.getDocuments()
        } catch (e: Exception) {
            emptyList()
        }
    }
}