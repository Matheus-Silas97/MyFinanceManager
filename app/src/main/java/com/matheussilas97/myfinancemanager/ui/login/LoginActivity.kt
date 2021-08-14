package com.matheussilas97.myfinancemanager.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheussilas97.myfinancemanager.databinding.ActivityLoginBinding
import com.matheussilas97.myfinancemanager.ui.forgetpassword.ForgetPasswordActivity
import com.matheussilas97.myfinancemanager.ui.home.MainActivity
import com.matheussilas97.myfinancemanager.ui.register.RegisterActivity
import com.matheussilas97.myfinancemanager.util.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception


class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        onClick()
        observer()

    }


    private fun doLogin() {
        if (!viewModel.doLogin(
                binding.editEmail.text.toString(),
                binding.editPassword.text.toString(), this
            )
        ) {
            viewModel.validateError.observe(this, Observer {
                showToast(it)
            })
        } else {
            viewModel.signInStatus.observe(this, Observer {status->
                if (status) {
                    getNextActivity(MainActivity::class.java)
                } else {
                    viewModel.validateError.observe(this, Observer {
                        showToast(it)
                    })
                }
            })

        }
    }

    private fun onClick() {
        binding.btnLogin.setOnClickListener {
            doLogin()
        }

        binding.txtRegister.setOnClickListener {
            getNextActivity(RegisterActivity::class.java)
        }

        binding.txtForgetPassword.setOnClickListener {
            getNextActivity(ForgetPasswordActivity::class.java)
        }
    }

    private fun observer() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

}