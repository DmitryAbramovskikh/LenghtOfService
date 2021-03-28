package com.dmabram15.lenghtofservice.model

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class LongToDateConverter {
    companion object {
        var calendar = GregorianCalendar()

        @JvmStatic
        fun convert(date : Long) : String {
            val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
            return simpleDateFormat.format(Date(date))
        }

        @JvmStatic
        fun convertDifferent(different : Long) : String {
            calendar.time = java.util.Date(different)
            val years = calendar.get(Calendar.YEAR) - 1970
            val months = calendar.get(Calendar.MONTH)
            val days = calendar.get(Calendar.DAY_OF_MONTH)

            return "$years years $months months $days days."
        }
    }
}