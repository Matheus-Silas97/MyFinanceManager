package com.matheussilas97.myfinancemanager.ui.home

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.databinding.ActivityMainBinding
import com.matheussilas97.myfinancemanager.ui.expense.AddExpenseActivity
import com.matheussilas97.myfinancemanager.ui.revenue.AddRevenueActivity
import com.matheussilas97.myfinancemanager.util.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val naviController = findNavController(R.id.nav_host)
        binding.bottomNavigationView.setupWithNavController(naviController)

        onClick()

    }

    private fun onClick() {
        binding.expense.setOnClickListener {
            getNextActivity(AddExpenseActivity::class.java)
        }

        binding.revenue.setOnClickListener {
            getNextActivity(AddRevenueActivity::class.java)
        }
    }
}