package com.example.thread.data.repository.user

import com.example.thread.data.ApiService
import com.example.thread.data.RetrofitInstance
import com.example.thread.data.model.activity.FollowActivity
import com.example.thread.data.model.response.ResponseMessage
import com.example.thread.data.model.user.LoginRequest
import com.example.thread.data.model.user.LoginResponse
import com.example.thread.data.model.user.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val apiService: ApiService = RetrofitInstance.apiService,
) {
    // 1. Get User by Id
    fun getUser(userId: Int = 1): User? {
        return apiService.getUser(userId).execute().body()
    }

    // 2. Login authentication by username and password
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

    // 3. Follow another User
    fun followUser(
        targetUserId: Int,
        onResponse: () -> Unit = {},
    ) {
        val call = apiService.followUser(targetUserId)
        call.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>,
            ) {
                onResponse()
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
            }
        })
    }

    // 4. Get followers of specific user
    fun getFollowers(targetUserId: Int): List<FollowActivity>? {
        return apiService.getFollowers(targetUserId).execute().body()
    }

    // 5. Get followings of specific user
    fun getFollowings(targetUserId: Int): List<FollowActivity>? {
        return apiService.getFollowings(targetUserId).execute().body()
    }
}
