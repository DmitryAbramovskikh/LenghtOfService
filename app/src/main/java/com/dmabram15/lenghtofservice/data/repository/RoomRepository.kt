package com.dmabram15.lenghtofservice.data.repository

import com.dmabram15.lenghtofservice.viewModel.dataobject.Period

interface RoomRepository {
    fun getAllPeriods() : ArrayList<Period>
    fun savePeriod(period: Period)
    fun deletePeriod(period: Period)
    fun dropDatabase()
}