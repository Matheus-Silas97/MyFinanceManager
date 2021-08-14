package com.matheussilas97.myfinancemanager.ui.finance

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.myfinancemanager.model.FinanceModel

class FinancialViewModel : ViewModel() {

    var loading = MutableLiveData<Boolean>()

    fun expenseList(): MutableLiveData<List<FinanceModel>>? {
        val list = MutableLiveData<List<FinanceModel>>()
        return list
    }

    fun revenueList(): MutableLiveData<List<FinanceModel>>? {
        val list = MutableLiveData<List<FinanceModel>>()
        return null
    }
}