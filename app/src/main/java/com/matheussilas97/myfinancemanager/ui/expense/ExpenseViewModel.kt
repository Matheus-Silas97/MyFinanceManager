package com.matheussilas97.myfinancemanager.ui.expense

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.myfinancemanager.R
import java.math.BigDecimal
import java.util.*

class ExpenseViewModel : ViewModel() {

    val validateError = MutableLiveData<String>()

    fun addExpense(
        value: String,
        description: String,
        date: String,
        paid: Boolean,
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


}