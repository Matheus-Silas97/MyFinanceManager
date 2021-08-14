package com.matheussilas97.myfinancemanager.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

}