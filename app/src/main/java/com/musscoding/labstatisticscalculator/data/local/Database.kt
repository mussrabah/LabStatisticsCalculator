package com.musscoding.labstatisticscalculator.data.local

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.musscoding.labstatisticscalculator.data.local.entity.ParameterEntity
import com.musscoding.labstatisticscalculator.data.local.entity.StatsResultsEntity
import com.musscoding.labstatisticscalculator.util.DateConverter

@RequiresApi(Build.VERSION_CODES.O)
@Database(
    entities = [ParameterEntity::class, StatsResultsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class Database: RoomDatabase() {

    abstract val dao: Dao
}