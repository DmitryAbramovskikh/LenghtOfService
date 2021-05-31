package com.dmabram15.lenghtofservice.model.repository

import androidx.room.*

@Dao
interface PeriodsDAO {
    @Query("SELECT * FROM periods_table")
    fun getAll() : List<PeriodEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePeriod(periodEntity: PeriodEntity)

    @Delete
    fun deletePeriod(periodEntity: PeriodEntity)

    @Insert
    fun insertPeriod(periodEntity: PeriodEntity)

    @Query("DELETE from periods_table")
    fun dropDatabase()
}