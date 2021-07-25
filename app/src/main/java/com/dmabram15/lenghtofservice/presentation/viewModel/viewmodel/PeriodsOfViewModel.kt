package com.dmabram15.lenghtofservice.presentation.viewModel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.repository.Repository
import com.dmabram15.lenghtofservice.model.usecases.DeletePeriodUseCase
import com.dmabram15.lenghtofservice.model.usecases.GetPeriodsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PeriodsOfViewModel : ViewModel() {

    val periodsData = MutableLiveData<List<Period>>()

    fun fetchPeriods(repository: Repository) {
        viewModelScope.launch(Dispatchers.IO) {
            val periods = GetPeriodsUseCase().execute(repository)
            periodsData.postValue(periods)
        }
    }

    fun deletePeriod(repository: Repository, period: Period) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = DeletePeriodUseCase().execute(repository, period)
            if (result) {
                periodsData.postValue(GetPeriodsUseCase().execute(repository))
            }
        }
    }

    fun getPeriodIdFromPeriod(period: Period?) = when {
        period != null -> period.id
        else -> 0
    }
}