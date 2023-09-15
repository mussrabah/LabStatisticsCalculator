package com.musscoding.labstatisticscalculator.presentation.monthly_statistics_calculation

import android.os.Build
import androidx.annotation.RequiresApi
import com.musscoding.labstatisticscalculator.domain.model.Parameter
import com.musscoding.labstatisticscalculator.domain.model.StatsResults

@RequiresApi(Build.VERSION_CODES.O)
data class MonthlyStatisticsCalculationState constructor(
    val parameters: List<Parameter> = emptyList(),
    /*
    val parameterWithStats: Map<Parameter, Int> = parameters.associateWith {
        0
    }
     */
    val statResults: List<StatsResults> = emptyList(),
    val parameterWithStats: Map<Parameter, Int> = emptyMap()
)