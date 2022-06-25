package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.R
import com.example.instagram.data.User
import com.example.instagram.databinding.ActivityFirstSignupStep1Binding
import com.example.instagram.login.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class FirstSignUpStep1Activity : AppCompatActivity() {

    lateinit var binding : ActivityFirstSignupStep1Binding
    private var tab = arrayListOf("전화번호", "이메일")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstSignupStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewPager 연결
        val signUpVPAdapter = SignUpVPAdapter(this)
        binding.firstSignupStep1Vp.adapter = signUpVPAdapter

        // ViewPager와 TabLayout 연결
        TabLayoutMediator(binding.firstSignupStep1Tb, binding.firstSignupStep1Vp) {
                tab, position ->
            tab.text = this.tab[position]
        }.attach()

        // 로그인 화면 연결
        binding.firstSignupStep1LoginTv.setOnClickListener {
            startLoginActivity()
        }

        binding.firstSignupStep1BackIv.setOnClickListener {  // 뒤로가기하면 다시 로그인 화면으로
            finish()
        }

    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}