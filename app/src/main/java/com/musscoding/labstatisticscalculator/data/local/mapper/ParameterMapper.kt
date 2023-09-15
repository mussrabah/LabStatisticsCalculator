package com.musscoding.labstatisticscalculator.data.local.mapper

import com.musscoding.labstatisticscalculator.data.local.entity.ParameterEntity
import com.musscoding.labstatisticscalculator.domain.model.Parameter

fun ParameterEntity.toParameter(): Parameter {
    return Parameter(
        id = id!!,
        name = name
    )
}

fun Parameter.toParameterEntity(): ParameterEntity {
    return ParameterEntity(
        id = id,
        name = name
    )
}