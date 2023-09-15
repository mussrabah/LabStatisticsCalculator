package com.musscoding.labstatisticscalculator.domain.preferences

interface Preferences {
    fun loadFirstTimeStartup(): Boolean
    fun saveFirstTimeStartup(isFirstTimeStartup: Boolean)


    companion object {
        const val KEY_IS_FIRST_TIME_STARTUP = "key_is_first_time_startup"
    }
}