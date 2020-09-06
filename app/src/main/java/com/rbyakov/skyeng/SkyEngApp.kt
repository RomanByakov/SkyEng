package com.rbyakov.skyeng

import android.app.Application
import com.rbyakov.skyeng.di.AppModule
import com.rbyakov.skyeng.di.DI
import toothpick.Toothpick
import toothpick.configuration.Configuration

class SkyEngApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initAppScope()
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }

    private fun initAppScope() {
        val appScope = Toothpick.openScope(DI.APP_SCOPE)

        appScope.installModules(
            AppModule(this)
        )
    }
}