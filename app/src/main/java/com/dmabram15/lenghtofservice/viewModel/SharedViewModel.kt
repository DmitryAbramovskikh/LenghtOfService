package com.dmabram15.lenghtofservice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmabram15.lenghtofservice.model.PeriodOfService
import com.dmabram15.lenghtofservice.model.repository.RoomRepository
import com.dmabram15.lenghtofservice.model.repository.RoomRepositoryImpl
import com.dmabram15.lenghtofservice.view.interfaces.OnChangeListListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel(), OnChangeListListener {

    private val periodsLiveData = MutableLiveData<ArrayList<PeriodOfService>>()
    private var repository: RoomRepository = RoomRepositoryImpl()
    private var editableItem = MutableLiveData<PeriodOfService?>()

    private var idKey = 0

    init {
        loadData()
    }

    fun observableItem() = editableItem

    fun getPeriods(): MutableLiveData<ArrayList<PeriodOfService>> = periodsLiveData

    fun setPeriod(period: PeriodOfService) {
        val index = periodsLiveData.value?.indexOfFirst { period.id == it.id }
        if (index != -1 && index != null) {
            periodsLiveData.value?.removeAt(index)
        }
        periodsLiveData.value?.add(period)
    }

    private fun savePeriod(periodOfService: PeriodOfService) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.savePeriod(periodOfService)
        }
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (periodsLiveData.value == null) {
                val periods = repository.getAllPeriods()
                if (periods.size > 0) {
                    idKey = periods[periods.size - 1].id
                }
                periodsLiveData.postValue(periods)
            }
        }
    }

    fun saveData() {
        GlobalScope.launch(Dispatchers.IO) {
            val periods = periodsLiveData.value
            if (periods != null) {
                repository.dropDatabase()
                for (period in periods) {
                    savePeriod(period)
                }
            }
        }
    }

    fun checkPeriodsCollision(periodOfService: PeriodOfService): Boolean {
        periodsLiveData.value?.let { periods ->
            for (periodItem: PeriodOfService in periods) {
                if (periodItem.id == periodOfService.id) return true
                if (periodOfService.beginPeriod in periodItem.beginPeriod..periodItem.endPeriod)
                    return false
                if (periodOfService.endPeriod in periodItem.beginPeriod..periodItem.endPeriod)
                    return false
                if (periodItem.beginPeriod in periodOfService.beginPeriod..periodOfService.endPeriod)
                    return false
            }
        }
        return true
    }

    override fun delete(id: Int) {
            periodsLiveData.value = periodsLiveData.value?.filter { it.id != id } as ArrayList<PeriodOfService>
    }

    override fun edit(id: Int) {
        periodsLiveData.value?.first {it.id == id}?.let { period ->
            editableItem.value = period
        }
        editableItem.value = null
    }

    fun getNextId() = ++idKey
}