package com.dmabram15.lenghtofservice.model.repository

import com.dmabram15.lenghtofservice.model.PeriodOfService

interface RoomRepository {
    fun getAllPeriods() : ArrayList<PeriodOfService>
    fun savePeriod(periodOfService: PeriodOfService)
    fun deletePeriod(periodOfService: PeriodOfService)
    fun dropDatabase()
}