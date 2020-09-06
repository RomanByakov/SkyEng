package com.rbyakov.skyeng

import androidx.test.espresso.Espresso.onIdle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rbyakov.skyeng.ui.MainActivity
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkFinder() {
        onView(withId(R.id.searchEt))
            .perform(ViewActions.typeText("elephant"))
        Thread.sleep(2000) // have to use idle
        onView(withId(R.id.meanings)).check(matches(isDisplayed()))
    }

    @Test
    fun checkRussian() {
        onView(withId(R.id.searchEt))
            .perform(ViewActions.replaceText("синхрофазатрон"))
        Thread.sleep(2000) // have to use idle
        onView(withId(R.id.emptyList)).check(matches(isDisplayed()))
    }
}