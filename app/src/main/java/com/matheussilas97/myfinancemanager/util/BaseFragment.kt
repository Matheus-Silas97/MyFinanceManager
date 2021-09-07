package com.matheussilas97.myfinancemanager.util

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat

abstract class BaseFragment : Fragment() {

    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun getNextActivity(activity: Class<*>) {
        startActivity(Intent(requireContext(), activity))
    }

    fun setNoResultAdapter(recyclerView: RecyclerView, message: String, context: Context) {
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = NoResultAdapter(context, message)
        recyclerView.layoutManager = layoutManager
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