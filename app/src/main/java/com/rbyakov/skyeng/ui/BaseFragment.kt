package com.rbyakov.skyeng.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rbyakov.skyeng.di.DI
import com.rbyakov.skyeng.helpers.objectScopeName
import toothpick.Scope
import toothpick.Toothpick

private const val STATE_SCOPE_NAME = "state_scope_name"

abstract class BaseFragment : Fragment() {

    abstract val layoutRes: Int

    private var instanceStateSaved: Boolean = false

    protected open val parentScopeName: String by lazy {
        (parentFragment as? BaseFragment)?.fragmentScopeName
            ?: DI.APP_SCOPE
    }

    protected lateinit var scope: Scope
        private set

    private lateinit var fragmentScopeName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentScopeName = savedInstanceState?.getString(STATE_SCOPE_NAME) ?: objectScopeName()

//        scope = if (Toothpick.isScopeOpen(fragmentScopeName)) {
//            Toothpick.openScope(fragmentScopeName)
//        } else {
        scope =Toothpick.openScopes(parentScopeName, fragmentScopeName)
       // }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(layoutRes, container, false)

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
        outState.putString(STATE_SCOPE_NAME, fragmentScopeName)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needCloseScope()) {
            Toothpick.closeScope(scope.name)
        }
    }

    private fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved)
                || ((parentFragment as? BaseFragment)?.isRealRemoving() ?: false)

    private fun needCloseScope(): Boolean =
        when {
            activity?.isChangingConfigurations == true -> false
            activity?.isFinishing == true -> true
            else -> isRealRemoving()
        }

    open fun onBackPressed() {
    }
}