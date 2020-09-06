package com.rbyakov.skyeng

import com.rbyakov.skyeng.network.models.Word
import com.rbyakov.skyeng.ui.detail.DetailFragment
import com.rbyakov.skyeng.ui.main.MainFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    object MainScreen : SupportAppScreen() {
        override fun getFragment() = MainFragment()
    }

    data class DetailScreen(val word: Word) : SupportAppScreen() {
        override fun getFragment() = DetailFragment.newInstance(word)
    }
}