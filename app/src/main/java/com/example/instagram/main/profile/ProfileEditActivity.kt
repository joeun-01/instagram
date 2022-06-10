package com.example.instagram.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.databinding.ActivityProfileEditBinding
import com.example.instagram.main.MainActivity

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editCancelTv.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}