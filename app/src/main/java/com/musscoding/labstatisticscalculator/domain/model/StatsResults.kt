package com.musscoding.labstatisticscalculator.domain.model

import java.time.LocalDate

data class StatsResults(
    val id: Int?,
    val parameter: Parameter,
    val date: LocalDate,
    var total: Int
)