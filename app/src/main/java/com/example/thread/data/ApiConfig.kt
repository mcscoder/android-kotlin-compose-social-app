package com.example.thread.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // private val BASE_URL = "http://192.168.2.241:3000/api/"
    // private val BASE_URL = "http://10.42.0.1:3000/api/"
    private const val BASE_URL = "http://192.168.1.18:3000/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun publicResourceURL(url: String): String {
        return "$BASE_URL$url"
    }
}
