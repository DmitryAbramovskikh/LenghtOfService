package com.dmabram15.lenghtofservice.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PeriodEntity::class], version = 1)
abstract class PeriodsDatabase : RoomDatabase() {
    abstract fun periodsDao() : PeriodsDAO
}