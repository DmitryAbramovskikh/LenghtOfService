package com.dmabram15.lenghtofservice.viewModel.repository

import com.dmabram15.lenghtofservice.model.Period

interface Repository {
    fun getAllPeriods() : ArrayList<Period>
    fun savePeriod(period: Period)
    fun deletePeriod(period: Period)
    fun dropDatabase()
}