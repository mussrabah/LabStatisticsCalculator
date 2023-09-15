package com.musscoding.labstatisticscalculator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "parameter"
)
data class ParameterEntity(
    val name: String,
    @PrimaryKey val id: Int? = null
)