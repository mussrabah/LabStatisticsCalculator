package com.musscoding.labstatisticscalculator.data.preferences

import android.content.SharedPreferences
import com.musscoding.labstatisticscalculator.domain.preferences.Preferences

class DefaultPreference(
    private val sharedPref: SharedPreferences
): Preferences {
    override fun loadFirstTimeStartup(): Boolean {
        return sharedPref.getBoolean(
            Preferences.KEY_IS_FIRST_TIME_STARTUP,
            true
        )
    }

    override fun saveFirstTimeStartup(isFirstTimeStartup: Boolean) {
        sharedPref.edit()
            .putBoolean(Preferences.KEY_IS_FIRST_TIME_STARTUP, isFirstTimeStartup)
            .apply()
    }

}