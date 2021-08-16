package com.matheussilas97.myfinancemanager.ui.graphic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.databinding.FragmentGraphicBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        setPie()
        return binding.root
    }

    private fun setPie() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            val pie = AnyChart.pie()

            val listData = ArrayList<DataEntry>()
            listData.add(ValueDataEntry("Despesas pagas", viewModel.totalExpensePaid))
            listData.add(ValueDataEntry("Despesas não pagas", viewModel.totalExpenseNotPaid))
            listData.add(ValueDataEntry("Receitas recebidas", viewModel.totalRevenueReceveid))
            listData.add(
                ValueDataEntry(
                    "Receitas não recebidas",
                    viewModel.totalRevenueNotReceveid
                )
            )

            pie.data(listData)
            pie.legend().position("center-bottom")
                .itemsLayout(LegendLayout.VERTICAL)
                .align(Align.LEFT);

            binding.anyChartView.setChart(pie)
        }
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