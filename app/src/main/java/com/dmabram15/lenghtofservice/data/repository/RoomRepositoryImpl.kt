package com.dmabram15.lenghtofservice.data.repository

import com.dmabram15.lenghtofservice.App
import com.dmabram15.lenghtofservice.viewModel.dataobject.Period

class RoomRepositoryImpl : RoomRepository {

    private val periodsDatabase = App.getInstance().getDb()
    private var periodsDAO = periodsDatabase.periodsDao()

    override fun getAllPeriods(): ArrayList<Period> {
        return daoInModelConverter(periodsDAO.getAll())
    }

    override fun savePeriod(period: Period) {
        periodsDAO.insertPeriod(modelInDaoConverter(period))
    }

    override fun deletePeriod(period: Period) {
        periodsDAO.deletePeriod(modelInDaoConverter(period))
    }

    override fun dropDatabase() {
        periodsDAO.dropDatabase()
    }

    private fun modelInDaoConverter(period: Period): PeriodEntity {
        return PeriodEntity(
            period.id,
            period.beginPeriod,
            period.endPeriod,
            period.multiple
        )
    }

    private fun daoInModelConverter(periodEntities: List<PeriodEntity>)
            : ArrayList<Period> {
        return ArrayList(
            periodEntities.map {
                Period(
                    it.id,
                    it.beginPeriod,
                    it.endPeriod,
                    it.multiple,
                )
            }
        )
    }
}