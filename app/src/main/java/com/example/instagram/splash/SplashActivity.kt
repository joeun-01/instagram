package com.example.instagram.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.databinding.ActivitySplashBinding
import com.example.instagram.login.LoginActivity
import com.example.instagram.main.MainActivity

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splash()
    }

    private fun splash() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }, 3000)
    }
}