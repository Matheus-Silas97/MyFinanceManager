package com.matheussilas97.myfinancemanager.ui.finance

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.databinding.FragmentExpenseBinding
import com.matheussilas97.myfinancemanager.ui.expense.EditExpenseActivity
import com.matheussilas97.myfinancemanager.util.BaseFragment
import com.matheussilas97.myfinancemanager.util.Constants

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ExpenseFragment : BaseFragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentExpenseBinding
    private lateinit var viewModel: FinancialViewModel

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
        binding = FragmentExpenseBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[FinancialViewModel::class.java]

        buildExpenseList()

        return binding.root
    }

    private fun buildExpenseList() {
        viewModel.expenseList()?.observe(requireActivity(), Observer { data ->
            if (data.isNullOrEmpty()) {
                setNoResultAdapter(
                    binding.recyclerExpense,
                    getString(R.string.no_result_list),
                    requireContext()
                )
            } else {
                val adapter = FinanceAdapter()
                binding.recyclerExpense.layoutManager = LinearLayoutManager(context)
                binding.recyclerExpense.adapter = adapter
                adapter.setList(data)
                adapter.addOnItemClickListener(object : FinanceAdapter.OnItemClickListener {
                    override fun onClick(id: Int) {
                        val intent = Intent(requireContext(), EditExpenseActivity::class.java)
                        intent.putExtra(Constants.ID_FINANCE, id)
                        startActivity(intent)
                    }
                })
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExpenseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}