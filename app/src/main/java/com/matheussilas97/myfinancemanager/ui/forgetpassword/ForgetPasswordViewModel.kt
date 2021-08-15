package com.matheussilas97.myfinancemanager.ui.forgetpassword

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.util.FirebaseConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForgetPasswordViewModel : ViewModel() {

    val validateError = MutableLiveData<String>()
    var recoverStatus = MutableLiveData<Boolean>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private var auth = FirebaseConfig().getFirebaseAutenticacao()

    fun doRecover(email: String, context: Context): Boolean {
        loading.postValue(true)
        if (email.isEmpty()) {
            loading.postValue(false)
            validateError.value = context.getString(R.string.empty_email)
            return false
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                auth?.sendPasswordResetEmail(email)?.addOnCompleteListener { task: com.google.android.gms.tasks.Task<Void> ->
                    if (task.isSuccessful) {
                        recoverStatus.postValue(true)
                    } else {
                        recoverStatus.postValue(false)
                    }
                }
            }
            loading.postValue(false)
            return true
        }
    }
}