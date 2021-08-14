package com.matheussilas97.myfinancemanager.ui.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.myfinancemanager.R

class LoginViewModel : ViewModel() {

    val validateError = MutableLiveData<String>()

    fun doLogin(email: String, password: String, context: Context): Boolean {
        if (email.isEmpty()) {
            validateError.value = context.getString(R.string.empty_email)
            return false
        } else if (password.isEmpty()) {
            validateError.value = context.getString(R.string.empty_password)
            return false
        } else {
            return true
        }
    }
}