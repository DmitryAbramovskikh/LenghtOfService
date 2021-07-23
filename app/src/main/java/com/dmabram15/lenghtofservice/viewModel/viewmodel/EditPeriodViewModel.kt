package com.dmabram15.lenghtofservice.viewModel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.PeriodBuilder
import com.dmabram15.lenghtofservice.model.repository.Repository
import com.dmabram15.lenghtofservice.model.usecases.GetPeriodByIdUseCase
import com.dmabram15.lenghtofservice.model.usecases.GetPeriodsUseCase
import com.dmabram15.lenghtofservice.model.usecases.SavePeriodUseCase
import com.dmabram15.lenghtofservice.model.utils.matchers.PeriodMatcher
import com.dmabram15.lenghtofservice.viewModel.converters.DateConverter
import com.dmabram15.lenghtofservice.viewModel.stringproviders.MessageStringProvider
import com.dmabram15.lenghtofservice.viewModel.periodmatcher.MatcherResultMessage
import com.dmabram15.lenghtofservice.viewModel.periodmatcher.OnMatchEventHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditPeriodViewModel(
    private val stringProvider: MessageStringProvider
) : ViewModel() {

    private var beginDate: MutableLiveData<String> = MutableLiveData()
    private var endDate: MutableLiveData<String> = MutableLiveData()
    private var multiple: MutableLiveData<Float> = MutableLiveData()

    private var startErrorMessage: MutableLiveData<MatcherResultMessage> = MutableLiveData()
    private var endErrorMessage: MutableLiveData<MatcherResultMessage> = MutableLiveData()
    private var infoMessage: MutableLiveData<MatcherResultMessage> = MutableLiveData()

    private var periodBuilder = PeriodBuilder()

    private var matchEventHandler = OnMatchEventHandler(
        startErrorMessage,
        endErrorMessage,
        infoMessage,
        stringProvider
    )

    fun getBeginLD(): MutableLiveData<String> = beginDate
    fun getEndLD(): MutableLiveData<String> = endDate
    fun getMultiplyLD(): MutableLiveData<Float> = multiple

    fun setStartDate(startPeriod : Long, dateConverter: DateConverter) {
        periodBuilder.setBeginPeriod(startPeriod)
        beginDate.value = dateConverter.convert(startPeriod)
    }

    fun setEndDate(endPeriod : Long, dateConverter: DateConverter) {
        periodBuilder.setEndPeriod(endPeriod)
        endDate.value = dateConverter.convert(endPeriod)
    }

    fun setMultiple(multiple : Float) {
        periodBuilder.setMultiple(multiple)
    }

    fun onSaveClicked(repository: Repository) {
        tryToSavePeriod(repository)
    }

    private fun tryToSavePeriod(repository: Repository) {
        if (periodBuilder.isReadyToBuild()) {
            val period = periodBuilder.build()
            val periods = GetPeriodsUseCase().execute(repository)
            if (PeriodMatcher(matchEventHandler).matchPeriod(period, periods)) {
                viewModelScope.launch(Dispatchers.IO) {
                    SavePeriodUseCase().execute(repository, period)
                    infoMessage.postValue(MatcherResultMessage(stringProvider.getSuccessMessage()))
                }
            }
        } else infoMessage.value = MatcherResultMessage(stringProvider.getNotAllFieldsFilled())
    }

    fun setPeriodById(repository : Repository, periodId: Int, dateConverter: DateConverter) {
        if(periodId > 0) {
            periodBuilder.setId(periodId)

            fetchPeriodById(repository, periodId).let{
                periodBuilder.setBeginPeriod(it.beginPeriod)
                beginDate.value =  dateConverter.convert(it.beginPeriod)

                periodBuilder.setEndPeriod(it.beginPeriod)
                endDate.value = dateConverter.convert(it.endPeriod)

                periodBuilder.setMultiple(it.multiple)
                multiple.value = it.multiple
            }
        }
        else {
            var lastId = GetPeriodsUseCase().execute(repository).last().id
            periodBuilder.setId(++lastId)
        }
    }

    private fun fetchPeriodById(repository : Repository, periodId: Int): Period {
        return GetPeriodByIdUseCase().execute(repository, periodId)
    }

    fun getStartErrorMessage() = startErrorMessage
    fun getEndErrorMessage() = endErrorMessage
    fun getSuccessMessage() = infoMessage

}