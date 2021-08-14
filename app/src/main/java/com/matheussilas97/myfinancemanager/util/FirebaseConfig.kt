package com.matheussilas97.myfinancemanager.util

import com.google.firebase.auth.FirebaseAuth

class FirebaseConfig {

    private var auth: FirebaseAuth? = null
//    private var firebase: DatabaseReference? = null
//
//    //retorna a instancia do FirebaseDatabase
//    fun getFirebaseDatabase(): DatabaseReference? {
//        if (firebase == null) {
//            firebase = FirebaseDatabase.getInstance().getReference()
//        }
//        return firebase
//    }

    //retorna a instancia do FirebaseAuth
    fun getFirebaseAutenticacao(): FirebaseAuth? {
        if (auth == null) {
            auth = FirebaseAuth.getInstance()
        }
        return auth
    }
}