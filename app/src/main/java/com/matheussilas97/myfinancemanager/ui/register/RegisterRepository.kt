package com.matheussilas97.myfinancemanager.ui.register

import com.matheussilas97.myfinancemanager.model.RegisterModel
import com.matheussilas97.myfinancemanager.util.FirebaseConfig

class RegisterRepository {

    private val dataBase = FirebaseConfig().getFirebaseDatabase()

    fun saveUser(model: RegisterModel) {
        val id = FirebaseConfig().getFirebaseAutenticacao()?.currentUser?.uid
        dataBase?.collection("users")?.document(id.toString())?.set(model)
    }
}