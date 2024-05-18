package com.example.thread.data.model1

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("Response")
	val response: List<ResponseItem?>? = null
)

data class ResponseItem(

	@field:SerializedName("post")
	val post: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null
)
