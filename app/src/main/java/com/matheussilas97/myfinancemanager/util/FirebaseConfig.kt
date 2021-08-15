package com.matheussilas97.myfinancemanager.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseConfig {

    private var auth: FirebaseAuth? = null
    private var firebase: FirebaseFirestore? = null

    //retorna a instancia do FirebaseDatabase
    fun getFirebaseDatabase(): FirebaseFirestore? {
        if (firebase == null) {
            firebase = FirebaseFirestore.getInstance()
        }
        return firebase
    }

    //retorna a instancia do FirebaseAuth
    fun getFirebaseAutenticacao(): FirebaseAuth? {
        if (auth == null) {
            auth = FirebaseAuth.getInstance()
        }
        return auth
    }
}