package com.dmabram15.lenghtofservice.viewModel.periodmatcher

import androidx.lifecycle.MutableLiveData
import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.listeners.OnMatcherEventListener
import com.dmabram15.lenghtofservice.viewModel.stringproviders.MessageStringProvider

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
        endErrorMessage.value =
            MatcherResultMessage(stringProvider.getCrossingMessage())
    }

    override fun hasIncludingCross(crossedPeriod: Period) {
        startErrorMessage.value =
            MatcherResultMessage(stringProvider.getCrossingMessage())
        endErrorMessage.value =
            MatcherResultMessage(stringProvider.getCrossingMessage())
    }

    override fun success() {
        successMessage.value =
            MatcherResultMessage(stringProvider.getSuccessMessage())
    }
}