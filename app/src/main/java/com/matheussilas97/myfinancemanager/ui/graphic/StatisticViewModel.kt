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

    var totalExpensePaid: Double = 0.0
    var totalExpenseNotPaid: Double = 0.0
    var totalRevenueReceveid: Double = 0.0
    var totalRevenueNotReceveid: Double = 0.0

    fun expenseList(): MutableLiveData<List<FinanceModel>> {
        val list = MutableLiveData<List<FinanceModel>>()
        repoExpense.getAllExpense()?.addSnapshotListener { value, error ->
            value?.toObjects(FinanceModel::class.java)?.let { it ->
                list.value = it
                totalExpensePaid = calculateValueTotal(it, true)
                totalExpenseNotPaid = calculateValueTotal(it, false)
            }
        }


        return list
    }

    fun revenueList(): MutableLiveData<List<FinanceModel>> {
        val list = MutableLiveData<List<FinanceModel>>()
        repoRevenue.getAllRevenue()?.addSnapshotListener { value, error ->
            value?.toObjects(FinanceModel::class.java)?.let { it ->
                list.value = it
                totalRevenueReceveid = calculateValueTotal(it, true)
                totalRevenueNotReceveid = calculateValueTotal(it, false)
            }
        }
        return list
    }

    private fun calculateValueTotal(list: List<FinanceModel>, finalized: Boolean): Double {
        var total = 0.0
        list.forEach {
            if (it.situation == finalized) {
                total += it.value
            }
        }
        return total
    }
}