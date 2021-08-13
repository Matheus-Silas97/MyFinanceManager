package com.matheussilas97.myfinancemanager.util

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseFragment : Fragment() {

    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun setNoResultAdapter(recyclerView: RecyclerView, message: String) {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = NoResultAdapter(requireContext(), message)
        recyclerView.layoutManager = layoutManager
    }

}