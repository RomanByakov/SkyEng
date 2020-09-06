package com.rbyakov.skyeng.di

import com.rbyakov.skyeng.BuildConfig
import com.rbyakov.skyeng.network.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class RetrofitProvider @Inject constructor(private val client: OkHttpClient) : Provider<Api> {

    override fun get(): Api =
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
}