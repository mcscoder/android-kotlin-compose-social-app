package com.example.thread.data

import com.example.thread.data.model.activity.FollowActivity
import com.example.thread.data.model.activity.ReplyActivity
import com.example.thread.data.model.response.ResponseMessage
import com.example.thread.data.model.thread.MainThreadWithRepliesResponse
import com.example.thread.data.model.thread.ThreadResponse
import com.example.thread.data.model.thread.ThreadRequest
import com.example.thread.data.model.user.UserLoginRequest
import com.example.thread.data.model.user.UserReplies
import com.example.thread.data.model.user.UserResponse
import com.example.thread.ui.screen.GlobalViewModelProvider
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

    @Multipart
    @POST("upload/images")
    fun uploadImages(@Part images: List<MultipartBody.Part>): Call<List<String>>

    // 1.1. Get User by `userId`
    @GET("user")
    fun getUser(
        @Header("targetUserId") targetUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<UserResponse>

    // 1.2. User login authentication
    @POST("user/authentication/login")
    fun userLoginAuthentication(@Body requestBody: UserLoginRequest): Call<Int>

    // 2.1. Get Thread by `threadId`
    @GET("thread/{threadId}")
    fun getThread(
        @Path("threadId") threadId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<ThreadResponse>

    // 2.2. Get a random list of Thread posts
    @GET("threads/random")
    fun getRandomThreads(
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<ThreadResponse>>

    // 2.3. Post a Thread
    @POST("thread/post")
    fun postThread(
        @Body requestBody: ThreadRequest,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<Unit>

    // 2.4. Favorite or unfavorite a Thread
    @GET("thread/favorite/{threadId}/{isFavorited}")
    fun favoriteThread(
        @Path("threadId") threadId: Int,
        @Path("isFavorited") isFavorited: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<Unit>

    // 2.5. Get a list of comments|replies by `mainId`
    @GET("thread/replies/{mainId}")
    fun getReplies(
        @Path("mainId") mainId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<ThreadResponse>>

    // 2.6. Get a list of Posts|Comments|Replies by `userId`
    @GET("threads/user/{type}")
    fun getThreadsByUserId(
        @Header("targetUserId") targetUserId: Int,
        @Path("type") type: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<ThreadResponse>>

    // 2.7. Get all Replies by `userId` included Main Thread
    @GET("threads/replies")
    fun getMainThreadWithReplies(
        @Header("targetUserId") targetUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<MainThreadWithRepliesResponse>>

    // temp
    @GET("thread/replying/reply/{threadReplyId}")
    fun getThreadReplyingReplies(
        @Path("threadReplyId") threadReplyId: Int,
        @Header("userId") userId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<ThreadResponse>>

    // temp, remove
    // @GET("thread/favorite/{threadId}")
    // fun favoriteThread(
    //     @Path("threadId") threadId: Int,
    //     @Query("isFavorite") isFavorite: Boolean? = null,
    //     @Header("userId") userId: Int = GlobalViewModelProvider.getCurrentUserId(),
    // ): Call<ResponseMessage>

    // temp
    @GET("thread/reply/favorite/{threadReplyId}")
    fun favoriteThreadReply(
        @Path("threadReplyId") threadId: Int,
        @Query("isFavorite") isFavorite: Boolean? = null,
        @Header("userId") userId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<ResponseMessage>

    // temp
    @GET("thread/replying/reply/favorite/{threadReplyingReplyId}")
    fun favoriteThreadReplyingReply(
        @Path("threadReplyingReplyId") threadId: Int,
        @Query("isFavorite") isFavorite: Boolean? = null,
        @Header("userId") userId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<ResponseMessage>

    // temp, remove
    // @POST("thread")
    // fun postThread(@Body newThreadRequest: ThreadRequest): Call<ResponseMessage>

    // temp
    @POST("thread/reply")
    fun postThreadReply(@Body newThreadRequest: ThreadRequest): Call<ResponseMessage>

    // temp
    @POST("thread/replying/reply")
    fun postThreadReplyingReply(@Body newThreadRequest: ThreadRequest): Call<ResponseMessage>

    // temp
    @GET("user/threads")
    fun getThreadsByUser(
        @Header("profileUserId") profileUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<ThreadResponse>>

    // temp
    @GET("user/replies")
    fun getRepliesByUser(
        @Header("profileUserId") profileUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<UserReplies>>

    // ----- activity
    // temp
    @GET("activity/replies")
    fun getReplyActivities(
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<ReplyActivity>>

    // ----- follows
    // temp
    @GET("follow")
    fun followUser(
        @Header("targetUserId") targetUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<ResponseMessage>

    @GET("followers")
    // temp
    fun getFollowers(
        @Header("targetUserId") targetUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<FollowActivity>>

    @GET("followings")
    // temp
    fun getFollowings(
        @Header("targetUserId") targetUserId: Int,
        @Header("currentUserId") currentUserId: Int = GlobalViewModelProvider.getCurrentUserId(),
    ): Call<List<FollowActivity>>
}

fun main() {
    // val newThreadRequestRequestRequest = ThreadRequest(
    //     text = "CMM",
    //     userId = 1,
    // )
    // runBlocking {
    //     val response = RetrofitInstance.apiService.getUser(1).execute()
    //     if (response.isSuccessful) {
    //         println(response.body()!!.firstName)
    //     }
    // }
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
