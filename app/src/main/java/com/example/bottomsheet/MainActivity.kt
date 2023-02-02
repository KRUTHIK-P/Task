package com.example.bottomsheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bottomsheet.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.addImageBtn.setOnClickListener {
            val bottomSheet = BottomSheetFragment(binding.image)
            bottomSheet.show(supportFragmentManager, "TAG")
        }
    }


}