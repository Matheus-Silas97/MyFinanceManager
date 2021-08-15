package com.matheussilas97.myfinancemanager.ui.revenue

import com.matheussilas97.myfinancemanager.model.FinanceModel
import com.matheussilas97.myfinancemanager.util.FirebaseConfig

class RevenueRepository {

    private val dataBase = FirebaseConfig().getFirebaseDatabase()
    private val auth = FirebaseConfig().getFirebaseAutenticacao()
    private val idUser = auth?.currentUser?.uid.toString()

    fun saveRevenue(model: FinanceModel) =
        dataBase?.collection("revenue")?.document(model.id!!)?.set(model)

    fun getAllRevenue() =
        dataBase?.collection("revenue")?.whereEqualTo("idUser", idUser)

    fun updateRevenue(id: String, model: FinanceModel) =
        dataBase?.collection("revenue")?.document(id)?.set(model)

    fun deleteRevenue(id: String) =
        dataBase?.collection("revenue")?.document(id)?.delete()

}