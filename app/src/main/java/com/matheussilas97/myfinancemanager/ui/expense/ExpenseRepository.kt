package com.matheussilas97.myfinancemanager.ui.expense

import com.matheussilas97.myfinancemanager.model.FinanceModel
import com.matheussilas97.myfinancemanager.util.FirebaseConfig
import java.util.*

class ExpenseRepository {

    private val dataBase = FirebaseConfig().getFirebaseDatabase()
    private val auth = FirebaseConfig().getFirebaseAutenticacao()
    val idUser = auth?.currentUser?.uid

    fun saveExpense(model: FinanceModel) =
        dataBase?.collection("expense")?.document(model.id!!)?.set(model)

    fun getAllExpense() =
        dataBase?.collection("expense")?.whereEqualTo("idUser", idUser)

    fun updateExpense(id: String, model: FinanceModel) =
        dataBase?.collection("expense")?.document(id)?.set(model)

    fun deleteExpense(id: String) =
        dataBase?.collection("expense")?.document(id)?.delete()

}