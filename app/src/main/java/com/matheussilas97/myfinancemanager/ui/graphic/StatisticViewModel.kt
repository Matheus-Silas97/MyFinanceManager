package com.matheussilas97.myfinancemanager.ui.graphic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.myfinancemanager.model.FinanceModel
import com.matheussilas97.myfinancemanager.ui.expense.ExpenseRepository
import com.matheussilas97.myfinancemanager.ui.revenue.RevenueRepository

class StatisticViewModel : ViewModel() {

    private val repoExpense = ExpenseRepository()
    private val repoRevenue = RevenueRepository()

    fun expenseList(): MutableLiveData<List<FinanceModel>> {
        val list = MutableLiveData<List<FinanceModel>>()
        repoExpense.getAllExpense()?.addSnapshotListener { value, error ->
            value?.toObjects(FinanceModel::class.java)?.let { it ->
                list.value = it
            }
        }
        return list
    }

    fun revenueList(): MutableLiveData<List<FinanceModel>> {
        val list = MutableLiveData<List<FinanceModel>>()
        repoRevenue.getAllRevenue()?.addSnapshotListener { value, error ->
            value?.toObjects(FinanceModel::class.java)?.let { it ->
                list.value = it
            }
        }
        return list
    }
}