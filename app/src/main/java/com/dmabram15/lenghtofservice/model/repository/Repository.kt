package com.dmabram15.lenghtofservice.model.repository

import com.dmabram15.lenghtofservice.model.Period

interface Repository {
    fun getAllPeriods() : ArrayList<Period>
    fun getPeriodById(periodId : Int) : Period
    fun savePeriod(period: Period) : Long
    fun deletePeriod(period: Period) : Int
    fun dropDatabase()
}