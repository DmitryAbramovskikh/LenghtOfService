package com.dmabram15.lenghtofservice.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PeriodOfService(
    val id : Int,
    val beginPeriod : Long,
    val endPeriod : Long,
    val multiple : Float
) : Parcelable {

    //Для использования в RecyclerView DiffUtils
    override fun equals(other: Any?): Boolean {
        if (this.javaClass != other?.javaClass)
        {
            return false
        }

        other as PeriodOfService

        if (this.id != other.id) return false
        if (this.beginPeriod != other.beginPeriod) return false
        if (this.endPeriod != other.endPeriod) return false
        if (this.multiple != other.multiple) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}