package com.rbyakov.skyeng

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        onView(withId(R.id.searchEt)).perform(typeText("elephant"))
    }
}