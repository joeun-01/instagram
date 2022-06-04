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

            autoLogin()  // 3초간 splash 화면을 띄운 후 다음으로 넘어감

        }, 3000)
    }

    private fun autoLogin() {  // 자동로그인 기능
        val userSP = getSharedPreferences("user", MODE_PRIVATE)
        val userIdx = userSP.getInt("userIdx", 0)

        if(userIdx == 0) {  // userIdx가 없으면 로그인이 안된 상태 -> 로그인 창으로
            startActivity(Intent(this, LoginActivity::class.java))
        }
        else {  // userIdx가 있으면 로그인이 된 상태 -> 홈 화면으로
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}