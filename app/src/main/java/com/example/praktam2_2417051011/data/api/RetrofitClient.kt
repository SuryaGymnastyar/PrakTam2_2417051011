package com.example.praktam2_2417051011.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL =
        "https://gist.githubusercontent.com/SuryaGymnastyar/fd9dbf080fae269b4aa3ea2872b6d4c5/raw/bc1c5e8c3523481cf062911d9ff3760cac507028/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}