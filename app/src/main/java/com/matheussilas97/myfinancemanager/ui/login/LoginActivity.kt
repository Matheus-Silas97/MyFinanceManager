package com.matheussilas97.myfinancemanager.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheussilas97.myfinancemanager.databinding.ActivityLoginBinding
import com.matheussilas97.myfinancemanager.ui.forgetpassword.ForgetPasswordActivity
import com.matheussilas97.myfinancemanager.ui.home.MainActivity
import com.matheussilas97.myfinancemanager.ui.register.RegisterActivity
import com.matheussilas97.myfinancemanager.util.BaseActivity

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        onClick()
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
            getNextActivity(MainActivity::class.java)
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
}