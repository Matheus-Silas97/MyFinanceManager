package com.matheussilas97.myfinancemanager.ui.forgetpassword

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.myfinancemanager.R

class ForgetPasswordViewModel : ViewModel() {

    val validateError = MutableLiveData<String>()

    fun doRecover(email: String, context: Context): Boolean {
        if (email.isEmpty()) {
            validateError.value = context.getString(R.string.empty_email)
            return false
        } else {
            return true
        }
    }
}