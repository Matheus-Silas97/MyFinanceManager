package com.matheussilas97.myfinancemanager.ui.home

import android.os.Bundle
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
        infoList()

        return binding.root
    }

    private fun infoList() {
        viewModel.expenseList().observe(requireActivity(), Observer {
            expenseList = it
        })

        viewModel.revenueList().observe(this, Observer {
            revenueList = it
        })
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

    private fun setupResumeCard() {
        val expensesValue = expenseList.fold(0L) { acc, item ->
            var res = acc

            if (!item.situation) res += item.value

            res
        }
        val revenueValue = revenueList.fold(0L) { acc, item ->
            var res = acc

            if (item.situation) res +=  item.value

            res
        }
        val result = revenueValue - expensesValue

        binding.txtValueTotal.text = "Saldo total: R$$result"
        binding.txtExpense.text = "Total Despesas: R$${expensesValue.toDouble()}"
        binding.txtRevenue.text ="Total Receitas: R$${revenueValue.toDouble()}"

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