package com.rbyakov.skyeng.network

import com.rbyakov.skyeng.network.models.Word
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("api/public/v1/words/search")
    suspend fun search(
        @Query("search") search: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<List<Word>>

}