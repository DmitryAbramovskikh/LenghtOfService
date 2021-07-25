package com.dmabram15.lenghtofservice.presentation.viewModel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmabram15.lenghtofservice.model.repository.Repository
import com.dmabram15.lenghtofservice.model.usecases.ComputeFullLengthWithMultiplierUseCase
import com.dmabram15.lenghtofservice.model.usecases.ComputeFullLengthWithoutMultiplierUseCase
import com.dmabram15.lenghtofservice.model.usecases.GetPeriodsUseCase
import com.dmabram15.lenghtofservice.presentation.viewModel.converters.DateConverter
import com.dmabram15.lenghtofservice.presentation.viewModel.stringproviders.CalendarStringProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LengthOfServiceViewModel : ViewModel() {

    val lengthWithMultiplier  = MutableLiveData<String>()
    val lengthInCalendar  = MutableLiveData<String>()

    fun fetchLengths(repository: Repository, stringProvider : CalendarStringProvider) {
        //Пока так до перехода на di насоздавал зависимостей
        viewModelScope.launch(Dispatchers.IO){
            val periods = GetPeriodsUseCase().execute(repository)
            val lengthInMillisMultiplier = ComputeFullLengthWithMultiplierUseCase().execute(periods)
            lengthWithMultiplier.postValue(DateConverter(stringProvider).convertDifferent(lengthInMillisMultiplier))

            val lengthInMillis = ComputeFullLengthWithoutMultiplierUseCase().execute(periods)
            lengthInCalendar.postValue(DateConverter(stringProvider).convertDifferent(lengthInMillis))
        }
    }
}