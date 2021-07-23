package com.dmabram15.lenghtofservice.view.stringprovider

import android.content.Context
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.viewModel.stringproviders.MessageStringProvider

class MessageStringProviderImpl(private val context : Context) : MessageStringProvider {

    override fun getCrossingMessage(): String =
        context.getString(R.string.crossing_period_error)

    override fun getNotAllFieldsFilled(): String =
        context.getString(R.string.not_all_fields_was_filled)

    override fun getSuccessMessage(): String =
        context.getString(R.string.saved_succesful)
}