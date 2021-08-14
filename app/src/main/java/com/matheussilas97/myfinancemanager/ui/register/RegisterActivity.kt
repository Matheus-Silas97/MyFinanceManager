package com.matheussilas97.myfinancemanager.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.databinding.ActivityRegisterBinding
import com.matheussilas97.myfinancemanager.ui.home.MainActivity
import com.matheussilas97.myfinancemanager.util.BaseActivity

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        onClick()
    }

    private fun doRegister() {
        if (!viewModel.doRegister(
                binding.editUsername.text.toString(),
                binding.editEmail.text.toString(),
                binding.editPassword.text.toString(), this
            )
        ) {
            viewModel.validateError.observe(this, Observer {
                showToast(it)
            })
        } else {
            showToast(getString(R.string.success_register))
            onBackPressed()
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
}