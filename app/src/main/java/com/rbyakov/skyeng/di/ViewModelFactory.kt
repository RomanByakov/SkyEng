package com.rbyakov.skyeng.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import toothpick.Toothpick
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory : ViewModelProvider.NewInstanceFactory {
    private val app: Application

    @Inject
    constructor(app: Application) : super() {
        this.app = app
    }

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        Toothpick.openScope(DI.APP_SCOPE).getInstance(modelClass) as T

}