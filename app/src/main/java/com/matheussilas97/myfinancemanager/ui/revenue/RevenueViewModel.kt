package com.matheussilas97.myfinancemanager.ui.revenue

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.myfinancemanager.R

class RevenueViewModel : ViewModel() {

    val validateError = MutableLiveData<String>()

    fun addRevenue(
        value: String,
        description: String,
        date: String,
        receveid: Boolean,
        context: Context
    ): Boolean {
        if (value.isEmpty()) {
            validateError.value = context.getString(R.string.empty_value)
            return false
        } else if (description.isEmpty()) {
            validateError.value = context.getString(R.string.empty_description)
            return false
        } else if (date.isEmpty()) {
            validateError.value = context.getString(R.string.empty_date)
            return false
        } else {
            return true
        }
    }

    fun editRevenue(
        value: String,
        description: String,
        date: String,
        receveid: Boolean,
        context: Context
    ): Boolean {
        if (value.isEmpty()) {
            validateError.value = context.getString(R.string.empty_value)
            return false
        } else if (description.isEmpty()) {
            validateError.value = context.getString(R.string.empty_description)
            return false
        } else if (date.isEmpty()) {
            validateError.value = context.getString(R.string.empty_date)
            return false
        } else {
            return true
        }
    }

    fun deleteRevenue() {

    }

}