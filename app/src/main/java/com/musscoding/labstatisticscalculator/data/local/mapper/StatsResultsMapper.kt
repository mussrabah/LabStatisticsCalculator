package com.musscoding.labstatisticscalculator.data.local.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.musscoding.labstatisticscalculator.data.local.entity.StatsResultsEntity
import com.musscoding.labstatisticscalculator.domain.model.Parameter
import com.musscoding.labstatisticscalculator.domain.model.StatsResults

fun StatsResultsEntity.toStatsResult(
    findParameterById: (Int)-> Parameter
): StatsResults {
    return StatsResults(
        id = id,
        parameter = findParameterById(parameter),
        date = date,
        total = total
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun StatsResults.toStatsResultsEntity(): StatsResultsEntity {
    return StatsResultsEntity(
        parameter = parameter.toParameterEntity().id ?: 0,
        date = date,
        total = total
    )
}