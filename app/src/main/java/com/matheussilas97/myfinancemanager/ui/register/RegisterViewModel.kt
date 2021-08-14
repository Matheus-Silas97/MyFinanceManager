package com.matheussilas97.myfinancemanager.ui.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.util.FirebaseConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterViewModel : ViewModel() {

    val validateError = MutableLiveData<String>()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var signUpStatus = MutableLiveData<Boolean>()
    private var auth = FirebaseConfig().getFirebaseAutenticacao()

    fun doRegister(email: String, password: String, context: Context): Boolean {
        loading.postValue(true)
        if (email.isEmpty()) {
            validateError.value = context.getString(R.string.empty_email)
            loading.postValue(false)
            return false
        } else if (password.isEmpty()) {
            validateError.value = context.getString(R.string.empty_password)
            loading.postValue(false)
            return false
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                auth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            signUpStatus.postValue(true)
                        } else {
                            try {
                                throw task.exception!!
                            } catch (e: FirebaseAuthWeakPasswordException) {
                                signUpStatus.postValue(false)
                                validateError.postValue("Digite uma senha mais forte!")
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                signUpStatus.postValue(false)
                                validateError.postValue("Por favor, digite um e-mail válido")
                            } catch (e: FirebaseAuthUserCollisionException) {
                                signUpStatus.postValue(false)
                                validateError.postValue("Este conta já foi cadastrada")
                            } catch (e: Exception) {
                                signUpStatus.postValue(false)
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