package com.musscoding.labstatisticscalculator.domain.repository

import com.musscoding.labstatisticscalculator.domain.model.Parameter
import com.musscoding.labstatisticscalculator.domain.model.StatsResults
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface Repository {

    suspend fun insertParameter(parameter: Parameter)

    suspend fun insertAllParameter(parameter: List<Parameter>)

    suspend fun insertStatsResult(result: List<StatsResults>)

    suspend fun deleteParameter(parameter: Parameter)

    suspend fun deleteStatsResultByDate(date: LocalDate)

    suspend fun deleteStatsResultByParamId(id: Int)

    fun getAllParameters(): Flow<List<Parameter>>

    fun getAllStatsByDate(date: LocalDate): Flow<List<StatsResults>>

    fun getAllStats(): Flow<List<StatsResults>>

    suspend fun updateParameter(parameter: Parameter)
}