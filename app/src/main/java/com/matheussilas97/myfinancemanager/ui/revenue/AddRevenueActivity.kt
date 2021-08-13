package com.matheussilas97.myfinancemanager.ui.revenue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.matheussilas97.myfinancemanager.databinding.ActivityAddRevenueBinding

class AddRevenueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRevenueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRevenueBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}