package com.matheussilas97.myfinancemanager.ui.revenue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheussilas97.myfinancemanager.databinding.ActivityAddRevenueBinding
import com.matheussilas97.myfinancemanager.util.BaseActivity

class AddRevenueActivity : BaseActivity() {

    private lateinit var binding: ActivityAddRevenueBinding
    private lateinit var viewModel: RevenueViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRevenueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RevenueViewModel::class.java]

        onClick()
        observer()
    }

    private fun addRevenue() {
        val value = binding.editValue.text.toString()
        val description = binding.editDescription.text.toString()
        val date = binding.editDate.text.toString()
        val paid = binding.switchPaid.isChecked
        if (!viewModel.addRevenue(value, description, date, paid, this)) {
            viewModel.validateError.observe(this, Observer {
                showToast(it)
            })
        }
    }

    private fun onClick() {
        binding.btnAdd.setOnClickListener {
            addRevenue()
        }

        binding.txtCancel.setOnClickListener {
            finish()
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.editDate.setOnClickListener {
            openCalendar(binding.editDate, this)
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