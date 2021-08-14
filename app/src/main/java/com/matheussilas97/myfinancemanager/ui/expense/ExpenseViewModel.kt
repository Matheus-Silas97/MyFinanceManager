package com.matheussilas97.myfinancemanager.ui.expense

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.myfinancemanager.R
import java.math.BigDecimal
import java.util.*

class ExpenseViewModel : ViewModel() {

    val validateError = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    fun addExpense(
        value: String,
        description: String,
        date: String,
        paid: Boolean,
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

    fun editExpense(
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

    fun deleteExpense(id: Int) {
        loading.postValue(true)
    }


}