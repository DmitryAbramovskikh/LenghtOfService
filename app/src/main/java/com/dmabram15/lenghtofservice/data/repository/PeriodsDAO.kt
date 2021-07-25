package com.dmabram15.lenghtofservice.data.repository

import androidx.room.*

@Dao
interface PeriodsDAO {
    @Query("SELECT * FROM periods_table")
    fun getAll() : List<PeriodEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePeriod(periodEntity: PeriodEntity)

    @Delete
    fun deletePeriod(periodEntity: PeriodEntity) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeriod(periodEntity: PeriodEntity) : Long

    @Query("DELETE from periods_table")
    fun dropDatabase()
}