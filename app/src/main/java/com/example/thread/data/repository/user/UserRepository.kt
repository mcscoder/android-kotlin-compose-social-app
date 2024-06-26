package com.example.thread.data.repository.user

import android.util.Log
import com.example.thread.data.ApiService
import com.example.thread.data.RetrofitInstance
import com.example.thread.data.model.activity.FollowActivity
import com.example.thread.data.model.common.ImageUrl
import com.example.thread.data.model.user.ActivityFollowResponse
import com.example.thread.data.model.user.UpdateProfileRequest
import com.example.thread.data.model.user.UserLoginRequest
import com.example.thread.data.model.user.UserRegisterRequest
import com.example.thread.data.model.user.UserResponse
import com.example.thread.data.repository.resource.ResourceRepository
import com.example.thread.ui.screen.GlobalViewModelProvider
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

class UserRepository(
    private val apiService: ApiService = RetrofitInstance.apiService,
    private val resourceRepository: ResourceRepository = ResourceRepository(),
) {
    // 1.1. Get User by `userId`
    fun getUser(targetUserId: Int = 1): UserResponse? {
        return apiService.getUser(targetUserId).execute().body()
    }

    // 1.2. User login authentication
    suspend fun loginAuthentication(requestBody: UserLoginRequest): Int? {
        return apiService.userLoginAuthentication(requestBody).body()
    }

    // 1.3. Follow or unfollow a User
    suspend fun followUser(targetUserId: Int) {
        apiService.followUser(targetUserId)
    }

    // 1.4. Get a list of user those who follow `target user`
    suspend fun getUserFollowers(targetUserId: Int): List<ActivityFollowResponse> {
        return apiService.getUserFollowers(targetUserId).body()!!
    }

    // 1.5. Get a list of user those who followed by `target user`
    suspend fun getUserFollowings(targetUserId: Int): List<ActivityFollowResponse> {
        return apiService.getUserFollowings(targetUserId).body()!!
    }

    // 1.6. Create new account
    suspend fun userRegister(requestBody: UserRegisterRequest): Int {
        return apiService.userRegister(requestBody).body()!!
    }

    // 1.7. Update User profile
    suspend fun updateUserProfile(requestBody: UpdateProfileRequest): Boolean {
        return apiService.updateUserProfile(requestBody).isSuccessful
    }

    // 1.8. Update User image
    suspend fun updateUserImage(imageFile: ByteArray): Boolean {
        val imageUrls = resourceRepository.uploadImages(listOf(imageFile))
        return apiService.updateUserImage(ImageUrl(imageUrls[0])).isSuccessful
    }

    // 1.9 Remove current User image
    suspend fun removeUserImage(): Boolean {
        return apiService.removeUserImage().isSuccessful
    }

    // 1.10. Check if email exists
    // Return true if email exists and otherwise is false
    suspend fun isEmailExists(email: String): Boolean {
        return apiService.isEmailExists(email).isSuccessful
    }

    // 1.11. Update new password
    suspend fun updateNewPassword(requestBody: UserLoginRequest) {
        apiService.updateNewPassword(requestBody)
    }
}
