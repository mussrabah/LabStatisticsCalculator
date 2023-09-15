package com.musscoding.labstatisticscalculator.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.musscoding.labstatisticscalculator.data.local.Dao
import com.musscoding.labstatisticscalculator.data.local.mapper.toParameter
import com.musscoding.labstatisticscalculator.data.local.mapper.toParameterEntity
import com.musscoding.labstatisticscalculator.data.local.mapper.toStatsResult
import com.musscoding.labstatisticscalculator.data.local.mapper.toStatsResultsEntity
import com.musscoding.labstatisticscalculator.domain.model.Parameter
import com.musscoding.labstatisticscalculator.domain.model.StatsResults
import com.musscoding.labstatisticscalculator.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class RepositoryImpl(
    private val dao: Dao
): Repository {


    override suspend fun insertParameter(parameter: Parameter) {
        dao.insertParameter(parameter.toParameterEntity())
    }

    override suspend fun insertAllParameter(parameters: List<Parameter>) {
        dao.insertAllParameter(parameters.map {
            it.toParameterEntity()
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun insertStatsResult(result: List<StatsResults>) {
        dao.insertStatsResult(
            result.map {
                it.toStatsResultsEntity()
        })
    }

    override suspend fun deleteParameter(parameter: Parameter) {
        dao.deleteParameter(parameter.toParameterEntity())
    }

    override suspend fun deleteStatsResultByDate(date: LocalDate) {
        dao.deleteStatsResultByDate(date)
    }

    override suspend fun deleteStatsResultByParamId(id: Int) {
        dao.deleteStatsResultByParamId(id)
    }

    override fun getAllParameters(): Flow<List<Parameter>> {
        return dao
            .getAllParameters()
            .map { entities->
                entities.map {
                    it.toParameter()
                }
            }
    }

    override fun getAllStatsByDate(date: LocalDate): Flow<List<StatsResults>> {
        return dao
            .getAllStatsByDate(date)
            .map { entities->
                entities.map {
                    it.toStatsResult {index->
                        Parameter(
                            id = index,
                            name = ""
                        )
                    }
                }
            }
    }

    override fun getAllStats(): Flow<List<StatsResults>> {
        return dao
            .getAllStats()
            .map {entities ->
                entities.map {
                    it.toStatsResult {index->
                        //dao.getParameterById(index).toParameter()
                        Parameter(
                            id = index,
                            name = ""
                        )
                    }
                }
            }
    }

    override suspend fun updateParameter(parameter: Parameter) {
        dao.updateParameter(parameter.toParameterEntity())
    }
}