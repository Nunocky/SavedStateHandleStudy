package com.example.myapplication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnIncrement.setOnClickListener {
            viewModel.incrementIntValue()
        }

        binding.btnDecrement.setOnClickListener {
            viewModel.decrementIntValue()
        }

        binding.btnDog.setOnClickListener {
            viewModel.setDogImage()
        }

        binding.btnCat.setOnClickListener {
            viewModel.setCatImage()
        }
    }
}