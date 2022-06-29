package com.example.instagram.main.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.databinding.ActivityAddPostBinding

class AddPostActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPostBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.addPostTopCloseIv.setOnClickListener {
            finish()
        }

    }

}