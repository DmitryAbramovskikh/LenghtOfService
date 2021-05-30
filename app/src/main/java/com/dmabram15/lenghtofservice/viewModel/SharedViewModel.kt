package com.dmabram15.lenghtofservice.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.lenghtofservice.model.PeriodOfService
import com.dmabram15.lenghtofservice.model.repository.RoomRepository
import com.dmabram15.lenghtofservice.model.repository.RoomRepositoryImpl
import com.dmabram15.lenghtofservice.view.interfaces.OnChangeListListener

class SharedViewModel : ViewModel(), OnChangeListListener {

    private val periodsLiveData = MutableLiveData<ArrayList<PeriodOfService>>()
    private var repository : RoomRepository = RoomRepositoryImpl()
    private var editableItem  = MutableLiveData<PeriodOfService?>()

    init {
        loadData()
    }

    fun observableItem() = editableItem

    fun getPeriods() : MutableLiveData<ArrayList<PeriodOfService>> = periodsLiveData

    fun setPeriod(value: PeriodOfService) {
        periodsLiveData.value?.add(value)
    }

    fun savePeriod(periodOfService: PeriodOfService) {
        Thread{
            repository.savePeriod(periodOfService)
        }.start()
    }

    fun loadData() {
        Thread{
            val periods = repository.getAllPeriods()
            periodsLiveData.postValue(periods)
        }.start()
    }

    fun checkPeriodsCollision(periodOfService : PeriodOfService): Boolean {
        periodsLiveData.value?.let{ periods ->
            for (periodItem : PeriodOfService in periods) {
                if (periodItem.id == periodOfService.id) return true
                if (periodOfService.beginPeriod in periodItem.beginPeriod .. periodItem.endPeriod)
                    return false
                if(periodOfService.endPeriod in periodItem.beginPeriod .. periodItem.endPeriod)
                    return false
                if (periodItem.beginPeriod in periodOfService.beginPeriod .. periodOfService.endPeriod)
                    return false
            }
        }
        return true
    }

    override fun delete(position: Int) {
        periodsLiveData.value?.let {
            it.remove(it[position])
        }
    }

    override fun edit(position: Int) {
        periodsLiveData.value?.get(position)?.let {
            editableItem.value = it
            editableItem.value = null
        }
    }
}