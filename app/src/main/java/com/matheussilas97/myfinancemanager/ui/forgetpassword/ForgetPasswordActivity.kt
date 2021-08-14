package com.matheussilas97.myfinancemanager.ui.forgetpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.databinding.ActivityForgetPasswordBinding
import com.matheussilas97.myfinancemanager.ui.home.MainActivity
import com.matheussilas97.myfinancemanager.util.BaseActivity

class ForgetPasswordActivity : BaseActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var viewModel: ForgetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ForgetPasswordViewModel::class.java]

        onClick()

    }

    private fun doRecover() {
        if (!viewModel.doRecover(
                binding.editEmail.text.toString(),
                this
            )
        ) {
            viewModel.validateError.observe(this, Observer {
                showToast(it)
            })
        } else {
            showToast(getString(R.string.success_recover))
            onBackPressed()
        }
    }

    private fun onClick() {
        binding.btnRecover.setOnClickListener {
            doRecover()
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}