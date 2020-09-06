package com.rbyakov.skyeng.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rbyakov.skyeng.R
import com.rbyakov.skyeng.di.AppLauncher
import com.rbyakov.skyeng.di.DI
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appLauncher: AppLauncher

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    private val navigator: Navigator =
        object : SupportAppNavigator(this, supportFragmentManager,
            R.id.container
        ) {

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        Toothpick.inject(this, Toothpick.openScope(DI.APP_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            Thread.sleep(2000) // simulate huge activity to show splash
            appLauncher.coldStart()
        }
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}