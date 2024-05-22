package com.example.thread.data

import com.example.thread.data.model.activity.FollowActivity
import com.example.thread.data.model.activity.ReplyActivity
import com.example.thread.data.model.response.ResponseMessage
import com.example.thread.data.model.thread.Thread
import com.example.thread.data.model.thread.ThreadRequest
import com.example.thread.data.model.user.LoginRequest
import com.example.thread.data.model.user.LoginResponse
import com.example.thread.data.model.user.User
import com.example.thread.data.model.user.UserReplies
import com.example.thread.ui.screen.GlobalViewModelProvider
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("user")
    fun getUser(
        @Header("otherUserId") userId: Int,
        @Header("userId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<User>

    @POST("authentication/login")
    fun loginAuthentication(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("thread/{threadId}")
    fun getThread(@Path("threadId") threadId: Int, @Header("userId") userId: Int): Call<Thread>

    @GET("thread/random/{count}")
    fun getRandomThreads(
        @Path("count") count: Int,
        @Header("userId") userId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<Thread>>

    @GET("thread/replies/{mainThreadId}")
    fun getThreadReplies(
        @Path("mainThreadId") mainThreadId: Int,
        @Header("userId") userId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<Thread>>

    @GET("thread/replying/reply/{threadReplyId}")
    fun getThreadReplyingReplies(
        @Path("threadReplyId") threadReplyId: Int,
        @Header("userId") userId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<Thread>>

    @GET("thread/favorite/{threadId}")
    fun favoriteThread(
        @Path("threadId") threadId: Int,
        @Query("isFavorite") isFavorite: Boolean? = null,
        @Header("userId") userId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<ResponseMessage>

    @GET("thread/reply/favorite/{threadReplyId}")
    fun favoriteThreadReply(
        @Path("threadReplyId") threadId: Int,
        @Query("isFavorite") isFavorite: Boolean? = null,
        @Header("userId") userId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<ResponseMessage>

    @GET("thread/replying/reply/favorite/{threadReplyingReplyId}")
    fun favoriteThreadReplyingReply(
        @Path("threadReplyingReplyId") threadId: Int,
        @Query("isFavorite") isFavorite: Boolean? = null,
        @Header("userId") userId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<ResponseMessage>

    @POST("thread")
    fun postThread(@Body newThreadRequest: ThreadRequest): Call<ResponseMessage>

    @POST("thread/reply")
    fun postThreadReply(@Body newThreadRequest: ThreadRequest): Call<ResponseMessage>

    @POST("thread/replying/reply")
    fun postThreadReplyingReply(@Body newThreadRequest: ThreadRequest): Call<ResponseMessage>

    @Multipart
    @POST("upload/images")
    fun uploadImages(@Part images: List<MultipartBody.Part>): Call<List<Int>>

    @GET("user/threads")
    fun getThreadsByUser(
        @Header("profileUserId") profileUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<Thread>>

    @GET("user/replies")
    fun getRepliesByUser(
        @Header("profileUserId") profileUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<UserReplies>>

    // ----- activity
    @GET("activity/replies")
    fun getReplyActivities(
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<ReplyActivity>>

    // ----- follows
    @GET("follow")
    fun followUser(
        @Header("targetUserId") targetUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<ResponseMessage>

    @GET("followers")
    fun getFollowers(
        @Header("targetUserId") targetUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<FollowActivity>>

    @GET("followings")
    fun getFollowings(
        @Header("targetUserId") targetUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<FollowActivity>>
}

fun main() {
    val newThreadRequestRequestRequest = ThreadRequest(
        text = "CMM",
        userId = 1,
    )
    runBlocking {
        val response = RetrofitInstance.apiService.getUser(1).execute()
        if (response.isSuccessful) {
            println(response.body()!!.firstName)
        }
    }
    // val call: Call<Thread> = RetrofitInstance.apiService.postThread(newThread)
    // call.enqueue(object : Callback<Thread> {
    //     override fun onResponse(
    //         call: Call<Thread>,
    //         response: Response<Thread>
    //     ) {
    //         if (response.isSuccessful) {
    //             val responseData: Thread? = response.body()
    //             if (responseData != null) {
    //                 println(responseData)
    //             }
    //         }
    //     }
    //
    //     override fun onFailure(call: Call<Thread>, t: Throwable) {
    //         println("i'm not gonna show up again")
    //     }
    // })

    // val call: Call<User> = RetrofitInstance.apiService.getUser(1)
    // call.enqueue(object : Callback<User> {
    //     override fun onResponse(
    //         call: Call<User>,
    //         response: Response<User>
    //     ) {
    //         if (response.isSuccessful) {
    //             val responseData: User? = response.body()
    //             if (responseData != null) {
    //                 println(responseData.username)
    //             }
    //         }
    //     }
    //
    //     override fun onFailure(call: Call<User>, t: Throwable) {
    //         println("i'm not gonna show up again")
    //     }
    // })
}
