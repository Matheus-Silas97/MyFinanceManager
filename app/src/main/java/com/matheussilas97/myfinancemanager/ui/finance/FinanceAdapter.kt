package com.matheussilas97.myfinancemanager.ui.finance

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheussilas97.myfinancemanager.databinding.ItemDetailBinding
import com.matheussilas97.myfinancemanager.model.FinanceModel

class FinanceAdapter() : RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder>() {

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

        holder.value.text = "R$${m.value}"
        holder.date.text = m.date.toString()
        holder.description.text = m.description

        if (m.type == "Expense") {
            when (m.situation) {
                true -> {
                    holder.situation.text = "Pago"
                }
                else -> {
                    holder.situation.text = "Não pago"
                }
            }
        } else {
            when (m.situation) {
                true -> {
                    holder.situation.text = "Recebido"
                }
                else -> {
                    holder.situation.text = "Não recebido"
                }
            }
        }

        holder.card.setOnClickListener {
            m.id?.let { it1 -> onItemClickLister?.onClick(it1) }
        }


    }

    override fun getItemCount(): Int = mList.size

    fun setList(list: List<FinanceModel>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(id: Int)
    }

    private var onItemClickLister: OnItemClickListener? = null

    fun addOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickLister = onItemClickListener
    }
}