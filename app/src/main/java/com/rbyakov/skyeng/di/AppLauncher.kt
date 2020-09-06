package com.rbyakov.skyeng.di

import com.rbyakov.skyeng.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AppLauncher @Inject constructor(
    private val router: Router
) {
    fun coldStart() {
        router.newRootScreen(Screens.MainScreen)
    }
}