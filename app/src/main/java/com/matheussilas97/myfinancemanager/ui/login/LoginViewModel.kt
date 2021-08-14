package com.matheussilas97.myfinancemanager.ui.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.util.FirebaseConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class LoginViewModel : ViewModel() {

    val validateError = MutableLiveData<String>()
    var signInStatus= MutableLiveData<Boolean>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private var auth = FirebaseConfig().getFirebaseAutenticacao()


    fun doLogin(
        email: String,
        password: String,
        context: Context
    ): Boolean {
        loading.postValue(true)

        if (email.isEmpty()) {
            loading.postValue(false)
            validateError.value = context.getString(R.string.empty_email)
            return false
        } else if (password.isEmpty()) {
            loading.postValue(false)
            validateError.value = context.getString(R.string.empty_password)
            return false
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                auth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            signInStatus.postValue(true)
                        } else {
                            try {
                                throw task.exception!!
                            } catch (e: FirebaseAuthInvalidUserException) {
                                signInStatus.postValue(false)
                                validateError.postValue("Usuário não está cadastrado")
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                signInStatus.postValue(false)
                                validateError.postValue("E-mail e senha não correspondem a um usuário cadastrado")
                            } catch (e: Exception) {
                                signInStatus.postValue(false)
                                validateError.postValue("Erro ao cadastrar usuário: " + e.message)
                                e.printStackTrace()
                            }
                        }
                    }
            }
            loading.postValue(false)
            return true
        }

    }
}