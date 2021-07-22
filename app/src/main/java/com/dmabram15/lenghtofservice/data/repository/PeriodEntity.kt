package com.dmabram15.lenghtofservice.data.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "periods_table")
data class PeriodEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "begin_period") val beginPeriod: Long,
    @ColumnInfo(name = "end_period") val endPeriod: Long,
    @ColumnInfo(name = "multiple") val multiple: Float
)