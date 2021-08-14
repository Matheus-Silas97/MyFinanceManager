package com.matheussilas97.myfinancemanager.ui.revenue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheussilas97.myfinancemanager.databinding.ActivityEditRevenueBinding
import com.matheussilas97.myfinancemanager.util.BaseActivity
import com.matheussilas97.myfinancemanager.util.Constants
import kotlin.properties.Delegates

class EditRevenueActivity : BaseActivity() {

    private lateinit var binding: ActivityEditRevenueBinding
    private lateinit var viewModel: RevenueViewModel
    private var idRevenue by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRevenueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RevenueViewModel::class.java]

        val data = intent.extras
        if (data != null) {
            idRevenue = data.getInt(Constants.ID_FINANCE)
        }

        onClick()
        observer()
    }

    private fun editRevenue() {
        val value = binding.editValue.text.toString()
        val description = binding.editDescription.text.toString()
        val date = binding.editDate.text.toString()
        val paid = binding.switchPaid.isChecked
        if (!viewModel.editRevenue(idRevenue, value, description, date, paid, this)) {
            viewModel.validateError.observe(this, Observer {
                showToast(it)
            })
        }
    }

    private fun onClick() {
        binding.btnAdd.setOnClickListener {
            editRevenue()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteRevenue(idRevenue)
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