package com.dicoding.dikasirind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.dikasirind.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        binding.buttonLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity :: class.java))
        }
        binding.imgCashier.setOnClickListener {
            startActivity(Intent(this, CashierActivity :: class.java))
        }
        binding.imgHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity :: class.java))
        }
        binding.imgPrint.setOnClickListener {
            startActivity(Intent(this,PrintActivity :: class.java))
        }
        binding.imgStock.setOnClickListener {
            startActivity(Intent(this, StockActivity :: class.java))
        }
    }
}