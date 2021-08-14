package com.matheussilas97.myfinancemanager.ui.forgetpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        observer()

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
            viewModel.recoverStatus.observe(this, Observer {
                if (it) {
                    showToast(getString(R.string.success_recover))
                    onBackPressed()
                } else {
                    showToast(getString(R.string.error_recover_password))
                }
            })

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