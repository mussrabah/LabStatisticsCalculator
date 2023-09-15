package com.musscoding.labstatisticscalculator.util

import com.musscoding.labstatisticscalculator.domain.model.Parameter

sealed class DialogTypes {
    object AddParameterDialog: DialogTypes()
    data class EditParameterDialog(val parameter: Parameter): DialogTypes()
    data class ShowParameterStatsDialog(val parameter: Parameter): DialogTypes()
}
