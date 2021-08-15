package com.matheussilas97.myfinancemanager.ui.expense

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.databinding.ActivityAddExpenseBinding
import com.matheussilas97.myfinancemanager.util.BaseActivity
import com.matheussilas97.myfinancemanager.util.MoneyMask

class AddExpenseActivity : BaseActivity() {

    private lateinit var binding: ActivityAddExpenseBinding
    private lateinit var viewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        onClick()
        observer()

    }

    private fun addExpense() {
        val value = binding.editValue.text.toString()
        val description = binding.editDescription.text.toString()
        val date = binding.editDate.text.toString()
        val paid = binding.switchPaid.isChecked
        if (!viewModel.addExpense(value, description, date, paid, this)) {
            viewModel.validateError.observe(this, Observer {
                showToast(it)
            })
        } else {
            viewModel.expenseStatus.observe(this, Observer { data ->
                if (data) {
                    showToast(getString(R.string.success_expense))
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
        binding.btnAdd.setOnClickListener {
            addExpense()
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