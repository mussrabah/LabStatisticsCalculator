package com.musscoding.labstatisticscalculator.presentation.see_results

import android.os.Build
import androidx.annotation.RequiresApi
import com.musscoding.labstatisticscalculator.domain.model.Parameter
import com.musscoding.labstatisticscalculator.domain.model.StatsResults
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
data class SeeResultsState(
    val globalStatsList: List<StatsResults> = emptyList(),
    val monthlyStatsList: List<StatsResults> = emptyList(),
    val globalParameterList: List<Parameter> = emptyList(),
    val monthlyParamWithStat: Map<Parameter, Int> = emptyMap(),
    val clickedOnParam: Parameter? = null,
    val percentage: Float = 0f,
    val date: LocalDate = LocalDate.now()
)
