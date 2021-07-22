package com.dmabram15.lenghtofservice.viewModel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmabram15.lenghtofservice.viewModel.dataobject.Period
import com.dmabram15.lenghtofservice.model.listeners.OnMatcherEventListener
import com.dmabram15.lenghtofservice.data.repository.RoomRepository
import com.dmabram15.lenghtofservice.data.repository.RoomRepositoryImpl
import com.dmabram15.lenghtofservice.model.utils.matchers.PeriodMatcher
import com.dmabram15.lenghtofservice.view.interfaces.OnChangeListListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel(), OnChangeListListener {

    private val periodsLiveData = MutableLiveData<ArrayList<Period>>()
    private var repository: RoomRepository = RoomRepositoryImpl()
    private var editableItem = MutableLiveData<Period?>()

    private var idKey = 0

    init {
        loadData()
    }

    fun observableItem() = editableItem

    fun getPeriods(): MutableLiveData<ArrayList<Period>> = periodsLiveData

    fun setPeriod(period: Period) {
        val index = periodsLiveData.value?.indexOfFirst { period.id == it.id }
        if (index != -1 && index != null) {
            periodsLiveData.value?.removeAt(index)
        }
        periodsLiveData.value?.add(period)
    }

    private fun savePeriod(period: Period) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.savePeriod(period)
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

    fun checkPeriodsCollision(period: Period, listener: OnMatcherEventListener): Boolean {
        val matcher = PeriodMatcher(listener)
        return matcher.matchPeriod(period, periodsLiveData.value)
    }

    override fun delete(id: Int) {
            periodsLiveData.value = periodsLiveData.value?.filter { it.id != id } as ArrayList<Period>
    }

    override fun edit(id: Int) {
        periodsLiveData.value?.first {it.id == id}?.let { period ->
            editableItem.value = period
        }
        editableItem.value = null
    }

    fun getNextId() = ++idKey
}