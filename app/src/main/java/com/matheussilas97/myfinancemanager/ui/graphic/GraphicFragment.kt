package com.matheussilas97.myfinancemanager.ui.graphic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.databinding.FragmentGraphicBinding
import org.koin.android.ext.android.bind

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GraphicFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentGraphicBinding
    private lateinit var viewModel: StatisticViewModel

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
        binding = FragmentGraphicBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(StatisticViewModel::class.java)

        setInfo()

        return binding.root
    }

    private fun setInfo() {
        viewModel.expenseList().observe(requireActivity(), Observer { data ->
            binding.totalExpense.text = "Total despesas: ${data.size} "
            binding.totalPaid.text =
                "Despesas pagas: ${data.filter { it.situation }.size.toString()}"
            binding.totalNotPaid.text =
                "Despesas pendentes: ${data.filter { !it.situation }.size.toString()}"
        })

        viewModel.revenueList().observe(requireActivity(), Observer { data ->
            binding.totalRevenue.text = "Total receitas: ${data.size}"
            binding.totalReceveid.text =
                "Receitas recebidas: ${data.filter { it.situation }.size.toString()}"
            binding.totalNotReceveid.text =
                "Receitas pendentes: ${data.filter { !it.situation }.size.toString()}"
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GraphicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}