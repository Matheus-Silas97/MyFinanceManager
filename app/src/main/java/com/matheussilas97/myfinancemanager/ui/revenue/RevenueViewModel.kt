package com.matheussilas97.myfinancemanager.ui.revenue

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.myfinancemanager.R

class RevenueViewModel : ViewModel() {

    val validateError = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    fun addRevenue(
        value: String,
        description: String,
        date: String,
        receveid: Boolean,
        context: Context
    ): Boolean {
        loading.postValue(true)
        if (value.isEmpty()) {
            validateError.value = context.getString(R.string.empty_value)
            loading.postValue(false)
            return false
        } else if (description.isEmpty()) {
            validateError.value = context.getString(R.string.empty_description)
            loading.postValue(false)
            return false
        } else if (date.isEmpty()) {
            validateError.value = context.getString(R.string.empty_date)
            loading.postValue(false)
            return false
        } else {
            loading.postValue(false)
            return true
        }
    }

    fun editRevenue(
        id: Int,
        value: String,
        description: String,
        date: String,
        receveid: Boolean,
        context: Context
    ): Boolean {
        loading.postValue(true)
        if (value.isEmpty()) {
            validateError.value = context.getString(R.string.empty_value)
            loading.postValue(false)
            return false
        } else if (description.isEmpty()) {
            validateError.value = context.getString(R.string.empty_description)
            loading.postValue(false)
            return false
        } else if (date.isEmpty()) {
            validateError.value = context.getString(R.string.empty_date)
            loading.postValue(false)
            return false
        } else {
            loading.postValue(false)
            return true
        }
    }

    fun deleteRevenue(id: Int) {
        loading.postValue(true)
    }

}