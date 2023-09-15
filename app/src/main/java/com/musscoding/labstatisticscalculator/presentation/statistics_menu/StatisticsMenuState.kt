package com.musscoding.labstatisticscalculator.presentation.statistics_menu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.font.FontVariation
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class StatisticsMenuState(
    val date: LocalDate = LocalDate.now(),
    val settings: List<Settings> = defaultSettings
)