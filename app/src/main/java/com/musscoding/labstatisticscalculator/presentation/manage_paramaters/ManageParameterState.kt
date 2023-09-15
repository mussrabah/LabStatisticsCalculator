package com.musscoding.labstatisticscalculator.presentation.manage_paramaters

import com.musscoding.labstatisticscalculator.domain.model.Parameter

data class ManageParameterState(
    val parameters: List<Parameter> = emptyList(),
    val clickedOnParameter: Parameter? = null
)
