package com.dmabram15.lenghtofservice.view.stringprovider

import android.content.Context
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.viewModel.stringproviders.CalendarStringProvider

class CalendarStringProviderImpl(private val context : Context) : CalendarStringProvider {
    override fun getYearsText(): String =
        context.getString(R.string.years)

    override fun getMonthsText(): String =
        context.getString(R.string.months)

    override fun getDaysText(): String =
        context.getString(R.string.days)
}