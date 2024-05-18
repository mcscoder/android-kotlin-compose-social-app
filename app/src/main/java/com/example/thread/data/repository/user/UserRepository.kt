package com.example.thread.data.repository.user

import android.util.Log
import com.example.thread.data.ApiService
import com.example.thread.data.RetrofitInstance
import com.example.thread.data.model.user.LoginRequest
import com.example.thread.data.model.user.LoginResponse
import com.example.thread.data.model.user.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val apiService: ApiService = RetrofitInstance.apiService,
) {
    fun getUser(userId: Int = 1): User? {
        return apiService.getUser(userId).execute().body()
    }

    fun loginAuthentication(
        loginRequest: LoginRequest,
        onResponse: (status: Boolean, data: LoginResponse) -> Unit,
    ) {
        val call = apiService.loginAuthentication(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                onResponse(response.isSuccessful, response.body()!!)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            }
        })
    }
}
