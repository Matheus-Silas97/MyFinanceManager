package com.matheussilas97.myfinancemanager.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.databinding.FragmentMainBinding
import com.matheussilas97.myfinancemanager.model.FinanceModel
import com.matheussilas97.myfinancemanager.ui.login.LoginActivity
import com.matheussilas97.myfinancemanager.util.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : BaseFragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var revenueList: List<FinanceModel>
    private lateinit var expenseList: List<FinanceModel>

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.toolbar.inflateMenu(R.menu.menu_logout)

        onClick()
        buildViewPager()
        infoList(binding)

        return binding.root
    }

    private fun infoList(binding: FragmentMainBinding) {
        viewModel.expenseList().observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty()) {
                expenseList = it
                binding.txtExpense.text = "R$${calculateExpenseTotal()}"
            } else {
                binding.txtExpense.text = "R$0.00"
            }
        })

        viewModel.revenueList().observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty()) {
                revenueList = it
                binding.txtRevenue.text = "R$${calculateRevenueTotal()}"
            } else {
                binding.txtExpense.text = "R$0.00"

            }
        })

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            if (::expenseList.isInitialized || ::revenueList.isInitialized) {
                infoTotal(binding)
            }
        }


    }

    private fun calculateExpenseTotal(): Double {
        var expenseSum = 0.0
        expenseList.forEach {
            if (it.situation) {
                expenseSum += it.value
            }
        }
        Log.i("teste", expenseSum.toString())
        return expenseSum
    }

    private fun calculateRevenueTotal(): Double {
        var revenueSum = 0.0
        revenueList.forEach {
            if (it.situation) {
                revenueSum += it.value
            }
        }
        return revenueSum
    }

    private fun infoTotal(binding: FragmentMainBinding) {
        if (calculateExpenseTotal() != null && calculateRevenueTotal() != null) {
            val result = calculateRevenueTotal() - calculateExpenseTotal()
            binding.txtTotal.text = "Total: R$$result"
        } else if (calculateExpenseTotal() != null && calculateRevenueTotal() == null) {
            binding.txtTotal.text = "Total: R$${calculateExpenseTotal()}"
        } else if (calculateRevenueTotal() != null && calculateExpenseTotal() == null) {
            binding.txtTotal.text = "Total: R$${calculateRevenueTotal()}"
        } else {
            binding.txtTotal.text = "Total: R$0.00"
        }
    }


    private fun buildViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(requireActivity())
        TabLayoutMediator(binding.tablayout, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.revenue)
            } else {
                tab.text = getString(R.string.expense)
            }

        }.attach()
    }

    private fun optionsToolbar(it: MenuItem): Boolean {
        return when (it.itemId) {
            R.id.ic_logout -> {
                FirebaseAuth.getInstance().signOut();
                getNextActivity(LoginActivity::class.java)
                requireActivity().finishAffinity()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun onClick() {
        binding.toolbar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener optionsToolbar(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}