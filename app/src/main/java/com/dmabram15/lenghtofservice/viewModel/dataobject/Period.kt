package com.dmabram15.lenghtofservice.viewModel.dataobject

data class Period(
    val id : Int,
    val beginPeriod : Long,
    val endPeriod : Long,
    val multiple : Float
) {

    //Для использования в RecyclerView DiffUtils
    override fun equals(other: Any?): Boolean {
        if (this.javaClass != other?.javaClass)
        {
            return false
        }

        other as Period

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