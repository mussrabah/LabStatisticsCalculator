package com.musscoding.labstatisticscalculator.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.musscoding.labstatisticscalculator.data.local.entity.ParameterEntity
import com.musscoding.labstatisticscalculator.data.local.entity.StatsResultsEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParameter(parameter: ParameterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllParameter(parameter: List<ParameterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatsResult(result: List<StatsResultsEntity>)

    @Delete
    suspend fun deleteParameter(parameter: ParameterEntity)

    @Query(
        """
            DELETE FROM stats_results
            WHERE date = :date
        """
    )
    suspend fun deleteStatsResultByDate(date: LocalDate)

    @Query(
        """
            DELETE FROM stats_results
            WHERE parameter_id = :id
        """
    )
    suspend fun deleteStatsResultByParamId(id: Int)

    @Query(
        """
            SELECT * FROM parameter
        """
    )
    fun getAllParameters(): Flow<List<ParameterEntity>>

    @Query(
        """
            SELECT * FROM stats_results
            WHERE date = :date
        """
    )
    fun getAllStatsByDate(date: LocalDate): Flow<List<StatsResultsEntity>>

    @Query(
        """
            SELECT * FROM stats_results
        """
    )
    fun getAllStats(): Flow<List<StatsResultsEntity>>

    @Query(
     """
         SELECT * FROM parameter
         WHERE id = :id
     """
    )
    fun getParameterById(id: Int): ParameterEntity

    @Update
    suspend fun updateParameter(parameter: ParameterEntity)
}