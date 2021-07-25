package com.dmabram15.lenghtofservice.presentation.viewModel.periodmatcher

import androidx.lifecycle.MutableLiveData
import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.listeners.OnMatcherEventListener
import com.dmabram15.lenghtofservice.presentation.viewModel.stringproviders.MessageStringProvider

class OnMatchEventHandler(
    private val startErrorMessage: MutableLiveData<MatcherResultMessage>,
    private val endErrorMessage: MutableLiveData<MatcherResultMessage>,
    private val successMessage: MutableLiveData<MatcherResultMessage>,
    private val stringProvider: MessageStringProvider
) : OnMatcherEventListener {
    override fun hasBeginCross(crossedPeriod: Period, date: Long) {
        startErrorMessage.value =
            MatcherResultMessage(stringProvider.getCrossingMessage())
    }

    override fun hasEndCross(crossedPeriod: Period, date: Long) {
        endErrorMessage.postValue(
            MatcherResultMessage(stringProvider.getCrossingMessage())
        )
    }

    override fun hasIncludingCross(crossedPeriod: Period) {
        startErrorMessage.postValue(
            MatcherResultMessage(stringProvider.getCrossingMessage())
        )
        endErrorMessage.postValue(
            MatcherResultMessage(stringProvider.getCrossingMessage())
        )
    }

    override fun success() {
        successMessage.postValue(
            MatcherResultMessage(stringProvider.getSuccessMessage())
        )
    }
}