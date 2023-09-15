package com.musscoding.labstatisticscalculator.presentation.monthly_statistics_calculation

import com.musscoding.labstatisticscalculator.domain.model.Parameter

sealed class MonthlyStatisticsCalculationEvents {
    data class OnParameterTap(val param: Parameter): MonthlyStatisticsCalculationEvents()
    data class OnParameterLongTap(val parameter: Parameter) : MonthlyStatisticsCalculationEvents()

    object OnSaveParamStat: MonthlyStatisticsCalculationEvents()
}
