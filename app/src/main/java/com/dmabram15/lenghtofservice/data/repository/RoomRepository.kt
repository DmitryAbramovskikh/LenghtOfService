package com.dmabram15.lenghtofservice.data.repository

import com.dmabram15.lenghtofservice.App
import com.dmabram15.lenghtofservice.model.repository.Repository
import com.dmabram15.lenghtofservice.model.Period

class RoomRepository private constructor() : Repository {

    private val periodsDatabase = App.getInstance().getDb()
    private var periodsDAO = periodsDatabase.periodsDao()

    //Добавить даггер
    private val memoryCachedPeriods : ArrayList<Period> = arrayListOf()

    override fun getAllPeriods(): ArrayList<Period> {
        if (memoryCachedPeriods.isEmpty()) {
            memoryCachedPeriods.addAll(daoInModelConverter(periodsDAO.getAll()))
        }
        return memoryCachedPeriods
    }

    override fun getPeriodById(periodId: Int): Period {
        return memoryCachedPeriods.first { it.id == periodId }
    }

    override fun savePeriod(period: Period) :Long {
        memoryCachedPeriods.add(period)
        return periodsDAO.insertPeriod(modelInDaoConverter(period))
    }

    override fun deletePeriod(period: Period) : Int {
        memoryCachedPeriods.remove(period)
        return periodsDAO.deletePeriod(modelInDaoConverter(period))
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

    companion object {
        private val instance = RoomRepository()
        fun getInstance() = instance
    }
}