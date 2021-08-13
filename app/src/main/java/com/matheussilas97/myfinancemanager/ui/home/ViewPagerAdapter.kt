package com.matheussilas97.myfinancemanager.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return RevenueFragment.newInstance("", "")
        } else {
            return ExpenseFragment.newInstance("", "")
        }
    }


}