package com.dmabram15.lenghtofservice.model.repository

import com.dmabram15.lenghtofservice.App
import com.dmabram15.lenghtofservice.model.PeriodOfService

class RoomRepositoryImpl : RoomRepository {

    private val periodsDatabase = App.getInstance().getDb()
    private var periodsDAO = periodsDatabase.periodsDao()

    override fun getAllPeriods(): ArrayList<PeriodOfService> {
        return daoInModelConverter(periodsDAO.getAll())
    }

    override fun savePeriod(periodOfService: PeriodOfService) {
        periodsDAO.insertPeriod(modelInDaoConverter(periodOfService))
    }

    override fun deletePeriod(periodOfService: PeriodOfService) {
        periodsDAO.deletePeriod(modelInDaoConverter(periodOfService))
    }

    override fun dropDatabase() {
        periodsDAO.dropDatabase()
    }

    private fun modelInDaoConverter(periodOfService: PeriodOfService): PeriodEntity {
        return PeriodEntity(
            periodOfService.id,
            periodOfService.beginPeriod,
            periodOfService.endPeriod,
            periodOfService.multiple
        )
    }

    private fun daoInModelConverter(periodEntities: List<PeriodEntity>)
            : ArrayList<PeriodOfService> {
        return ArrayList(
            periodEntities.map {
                PeriodOfService(
                    it.id,
                    it.beginPeriod,
                    it.endPeriod,
                    it.multiple
                )
            }
        )
    }
}