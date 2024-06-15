package com.example.thread.data.repository.resource

import com.example.thread.data.ApiService
import com.example.thread.data.RetrofitInstance
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ResourceRepository(
    private val apiService: ApiService = RetrofitInstance.apiService,
) {
    suspend fun uploadImages(imageFiles: List<ByteArray>): List<String> {
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        imageFiles.forEach { imageFile ->
            requestBody.addFormDataPart(
                "files",
                "${imageFile.hashCode()}.webp",
                imageFile.toRequestBody(
                    "image/*".toMediaType(),
                    0,
                    imageFile.size
                )
            )
        }
        return apiService.uploadImages(requestBody.build().parts).body()!!
    }

    // Get confirmation code
    suspend fun getConfirmationCode(email: String) {
        apiService.getConfirmationCode(email)
    }

    // Check confirmation code
    suspend fun checkConfirmationCode(email: String, code: Int): Boolean {
        return apiService.checkConfirmationCode(email, code).isSuccessful
    }
}
