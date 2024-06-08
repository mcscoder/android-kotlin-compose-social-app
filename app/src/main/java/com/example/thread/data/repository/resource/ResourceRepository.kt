package com.example.thread.data.repository.resource

import com.example.thread.data.ApiService
import com.example.thread.data.RetrofitInstance
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ResourceRepository(
    private val apiService: ApiService = RetrofitInstance.apiService,
) {
    fun uploadImages(imageFiles: List<ByteArray>): List<String> {
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
        val imageUrls = apiService.uploadImages(requestBody.build().parts).execute().body()
        return imageUrls!!
    }
}
