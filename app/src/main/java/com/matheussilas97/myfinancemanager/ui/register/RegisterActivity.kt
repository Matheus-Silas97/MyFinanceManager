package com.matheussilas97.myfinancemanager.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.databinding.ActivityRegisterBinding
import com.matheussilas97.myfinancemanager.ui.home.MainActivity
import com.matheussilas97.myfinancemanager.util.BaseActivity

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        onClick()
        observer()
    }

    private fun doRegister() {
        if (!viewModel.doRegister(
                binding.editEmail.text.toString(),
                binding.editPassword.text.toString(), this
            )
        ) {
            viewModel.validateError.observe(this, Observer {
                showToast(it)
            })
        } else {
            viewModel.signUpStatus.observe(this, Observer { status ->
                if (status) {
                    showToast(getString(R.string.success_register))
                    onBackPressed()
                } else {
                    viewModel.validateError.observe(this, Observer {
                        showToast(it)
                    })
                }
            })

        }
    }

    private fun onClick() {
        binding.btnRegister.setOnClickListener {
            doRegister()
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
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