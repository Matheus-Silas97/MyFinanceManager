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
import com.matheussilas97.myfinancemanager.databinding.FragmentRevenueBinding
import com.matheussilas97.myfinancemanager.model.FinanceModel
import com.matheussilas97.myfinancemanager.ui.expense.EditExpenseActivity
import com.matheussilas97.myfinancemanager.ui.revenue.EditRevenueActivity
import com.matheussilas97.myfinancemanager.util.BaseFragment
import com.matheussilas97.myfinancemanager.util.Constants

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RevenueFragment : BaseFragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentRevenueBinding
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
        binding = FragmentRevenueBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[FinancialViewModel::class.java]

        buildRevenueList()

        return binding.root
    }


    private fun buildRevenueList() {
        viewModel.revenueList().observe(requireActivity(), Observer { data ->
            if (!data.isNullOrEmpty()) {
                val adapter = FinanceAdapter(requireContext())
                binding.recyclerRevenue.layoutManager = LinearLayoutManager(context)
                binding.recyclerRevenue.adapter = adapter
                adapter.setList(data)
                adapter.addOnItemClickListener(object : FinanceAdapter.OnItemClickListener {
                    override fun onClick(id: String, model: FinanceModel) {
                        val intent = Intent(requireContext(), EditRevenueActivity::class.java)
                        intent.putExtra(Constants.ID_FINANCE, id)
                        intent.putExtra(Constants.MODEL_FINANCE, model)
                        startActivity(intent)
                    }
                })
            } else {
                setNoResultAdapter(
                    binding.recyclerRevenue,
                    getString(R.string.no_result_list),
                    requireContext()
                )
            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RevenueFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}