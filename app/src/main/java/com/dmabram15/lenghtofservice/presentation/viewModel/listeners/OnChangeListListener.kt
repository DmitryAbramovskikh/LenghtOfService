package com.dmabram15.lenghtofservice.presentation.viewModel.listeners

import com.dmabram15.lenghtofservice.model.Period

interface OnChangeListListener {
    fun delete(period: Period)
    fun edit(period: Period)
}
