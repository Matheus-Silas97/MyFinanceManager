package com.matheussilas97.myfinancemanager.ui.finance

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.matheussilas97.myfinancemanager.R
import com.matheussilas97.myfinancemanager.databinding.ItemDetailBinding
import com.matheussilas97.myfinancemanager.model.FinanceModel
import java.text.NumberFormat

class FinanceAdapter(val context: Context) :
    RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder>() {

    private val mList = mutableListOf<FinanceModel>()

    inner class FinanceViewHolder(binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val card = binding.cardview
        val value = binding.txtValue
        val date = binding.txtDate
        val description = binding.txtDescription
        val situation = binding.txtSituation
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceViewHolder {
        val binding =
            ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinanceViewHolder, position: Int) {
        val m = mList[position]

        holder.value.text = formatCurrency(context, m.value)
        holder.date.text = m.date
        holder.description.text = m.description

        if (m.type == "Expense") {
            holder.value.setTextColor(ContextCompat.getColor(context, R.color.red))
            when (m.situation) {
                true -> {
                    holder.situation.text = context.getString(R.string.paid)
                }
                else -> {
                    holder.situation.text = context.getString(R.string.pending)
                }
            }
        } else {
            holder.value.setTextColor(ContextCompat.getColor(context, R.color.green))
            when (m.situation) {
                true -> {
                    holder.situation.text = context.getString(R.string.charged)
                }
                else -> {
                    holder.situation.text = context.getString(R.string.pending)
                }
            }
        }

        holder.card.setOnClickListener {
            onItemClickLister?.onClick(m.id!!, m)
        }


    }

    override fun getItemCount(): Int = mList.size

    fun setList(list: List<FinanceModel>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(id: String, model: FinanceModel)
    }

    private var onItemClickLister: OnItemClickListener? = null

    fun addOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickLister = onItemClickListener
    }

    fun formatCurrency(context: Context, value: Double): String? {
        val currencyInstance: NumberFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NumberFormat.getCurrencyInstance(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) context.resources.configuration.locales[0] else context.resources.configuration.locale
            )
        } else NumberFormat.getCurrencyInstance()
        return currencyInstance.format(value)
    }
}