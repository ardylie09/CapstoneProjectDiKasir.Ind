package com.dicoding.dikasirind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.dikasirind.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAction()
    }

    private fun setAction() {
        binding.textViewStart.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}