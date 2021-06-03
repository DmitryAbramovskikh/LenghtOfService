package com.dmabram15.lenghtofservice.model.utils.matchers

import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.listeners.OnMatcherEventListener

class PeriodMatcher(
    private val listener: OnMatcherEventListener
) {

    fun matchPeriod(period : Period, existsPeriods : List<Period>?) : Boolean {
        existsPeriods?.let { periods ->
            for (periodItem: Period in periods) {
                if (periodItem.id == period.id) {
                    listener.success()
                    return true
                }
                if (period.beginPeriod in periodItem.beginPeriod..periodItem.endPeriod) {
                    listener.hasBeginCross(periodItem, period.beginPeriod)
                    return false
                }
                if (period.endPeriod in periodItem.beginPeriod..periodItem.endPeriod) {
                    listener.hasEndCross(periodItem, period.endPeriod)
                    return false
                }
                if (periodItem.beginPeriod in period.beginPeriod..period.endPeriod) {
                    listener.hasIncludingCross(periodItem)
                    return false
                }
            }
        }
        listener.success()
        return true
    }


}