package com.dmabram15.lenghtofservice.model.listeners

import com.dmabram15.lenghtofservice.model.Period

interface OnMatcherEventListener {
    fun hasBeginCross(crossedPeriod: Period, date : Long)
    fun hasEndCross(crossedPeriod: Period, date : Long)
    fun hasIncludingCross(crossedPeriod: Period)
    fun success()
}