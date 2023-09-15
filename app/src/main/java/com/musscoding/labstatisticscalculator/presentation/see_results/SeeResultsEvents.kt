package com.musscoding.labstatisticscalculator.presentation.see_results

import com.musscoding.labstatisticscalculator.domain.model.Parameter

sealed class SeeResultsEvents() {
    data class OnParamClick(val param: Parameter): SeeResultsEvents()
    object OnPreviousMonthClick: SeeResultsEvents()
    object OnNextMonthClick: SeeResultsEvents()
    object OnDeleteAllStats: SeeResultsEvents()
}
