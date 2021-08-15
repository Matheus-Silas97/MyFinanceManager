package com.matheussilas97.myfinancemanager.ui.revenue

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.model.FinanceModel
import com.matheussilas97.myfinancemanager.ui.expense.ExpenseRepository
import com.matheussilas97.myfinancemanager.util.FirebaseConfig
import java.util.*

class RevenueViewModel : ViewModel() {

    val validateError = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    val expenseStatus = MutableLiveData<Boolean>()
    private val repo = RevenueRepository()
    private val auth = FirebaseConfig().getFirebaseAutenticacao()

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
            val randomId = UUID.randomUUID().toString()
            val model =
                FinanceModel(
                    randomId,
                    auth?.currentUser?.uid.toString(),
                    value.toDouble(),
                    date,
                    description,
                    receveid,
                    "Revenue"
                )
            repo.saveRevenue(model)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    expenseStatus.value = true
                } else {
                    expenseStatus.value = false
                    validateError.value = context.getString(R.string.error)
                }
            }?.addOnFailureListener {
                expenseStatus.value = false
            }
            loading.postValue(false)
            return true
        }
    }

    fun editRevenue(
        id: String,
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
            val model =
                FinanceModel(
                    id,
                    auth?.currentUser?.uid.toString(),
                    value.toDouble(),
                    date,
                    description,
                    receveid,
                    "Revenue"
                )
            repo.updateRevenue(id, model)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    expenseStatus.value = true
                } else {
                    expenseStatus.value = false
                    validateError.value = context.getString(R.string.error)
                }
            }?.addOnFailureListener {
                expenseStatus.value = false
            }
            loading.postValue(false)
            return true
        }
    }

    fun deleteRevenue(id: String): MutableLiveData<Boolean> {
        var status = MutableLiveData<Boolean>()
        repo.deleteRevenue(id)?.addOnCompleteListener {
            status.value = it.isSuccessful
        }
        return status
    }

}