package com.matheussilas97.myfinancemanager.ui.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.myfinancemanager.R

class RegisterViewModel : ViewModel() {

    val validateError = MutableLiveData<String>()

    fun doRegister(userName: String, email: String, password: String, context: Context): Boolean {
        if (userName.isEmpty()) {
            validateError.value = context.getString(R.string.empty_username)
            return false
        } else if (email.isEmpty()) {
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