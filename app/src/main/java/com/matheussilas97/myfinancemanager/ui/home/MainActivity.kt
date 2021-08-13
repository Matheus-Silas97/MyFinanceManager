package com.matheussilas97.myfinancemanager.ui.home

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
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

        buildViewPager()
        onClick()

    }

    private fun buildViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tablayout, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.revenue)
            } else {
                tab.text = getString(R.string.expense)
            }

        }.attach()
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