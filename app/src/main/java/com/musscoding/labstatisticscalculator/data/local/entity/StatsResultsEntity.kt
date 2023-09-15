package com.musscoding.labstatisticscalculator.data.local.entity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Entity(
    tableName = "stats_results",
    foreignKeys = [
        ForeignKey(
            ParameterEntity::class,
            childColumns = ["parameter_id"],
            parentColumns = ["id"]
        )
    ]
)
data class StatsResultsEntity(
    @ColumnInfo(name = "parameter_id", index = true) val parameter: Int,
    val date: LocalDate,
    val total: Int,
    @PrimaryKey val id: Int? = null
)
