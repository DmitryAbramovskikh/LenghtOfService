package com.dmabram15.lenghtofservice.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PeriodOfService(
    val id : Int,
    val beginPeriod : Long,
    val endPeriod : Long,
    val multiple : Float
) : Parcelable