package com.matheussilas97.myfinancemanager.ui.revenue

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.databinding.ActivityEditRevenueBinding
import com.matheussilas97.myfinancemanager.model.FinanceModel
import com.matheussilas97.myfinancemanager.util.BaseActivity
import com.matheussilas97.myfinancemanager.util.Constants
import kotlin.properties.Delegates

class EditRevenueActivity : BaseActivity() {

    private lateinit var binding: ActivityEditRevenueBinding
    private lateinit var viewModel: RevenueViewModel
    private lateinit var idRevenue: String
    private lateinit var model: FinanceModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRevenueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RevenueViewModel::class.java]

        val data = intent.extras
        if (data != null) {
            idRevenue = data.getString(Constants.ID_FINANCE, "")
            model = data.getParcelable<FinanceModel>(Constants.MODEL_FINANCE)!!

            binding.editValue.setText(model.value.toString())
            binding.editDate.setText(model.date)
            binding.editDescription.setText(model.description)
            binding.switchPaid.isChecked = model.situation
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
        } else {
            viewModel.expenseStatus.observe(this, Observer { data ->
                if (data) {
                    showToast(getString(R.string.success_revenue_update))
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
            editRevenue()
        }

        binding.btnDelete.setOnClickListener {
            deleteFinance()
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

    private fun deleteFinance() {
        val alertDialog = android.app.AlertDialog.Builder(this).create()
        alertDialog.setTitle("Deletar")
        alertDialog.setMessage("Deseja apagar essa receita?")
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            "Apagar",
            DialogInterface.OnClickListener { dialogInterface, i ->
                viewModel.deleteRevenue(idRevenue).observe(this, Observer {
                    if (it) {
                        showToast(getString(R.string.success_delete))
                        onBackPressed()
                    } else {
                        showToast(getString(R.string.error))
                    }
                })
            })
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "Cancelar",
            DialogInterface.OnClickListener
            { dialog, which -> dialog.dismiss() })
        alertDialog.show()
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