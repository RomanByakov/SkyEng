package com.rbyakov.skyeng.di

import android.app.Application
import android.content.Context
import com.rbyakov.skyeng.network.Api
import com.rbyakov.skyeng.BuildConfig
import com.rbyakov.skyeng.SkyEngApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module
import java.util.concurrent.TimeUnit

class AppModule(context: Context) : Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(Application::class.java).toInstance(context as SkyEngApp)

        //Navigation
        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)

        bind(AppLauncher::class.java).singleton()

        bind(Api::class.java).toProvider(RetrofitProvider::class.java)

        val client = with(OkHttpClient.Builder()) {
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            }
            build()
        }

        bind(OkHttpClient::class.java).toInstance(client)
    }
}