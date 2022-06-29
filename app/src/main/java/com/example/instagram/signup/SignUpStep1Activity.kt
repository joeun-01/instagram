package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.databinding.ActivitySignupEmailBinding
import com.example.instagram.login.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator

class SignUpStep1Activity : AppCompatActivity() {

    lateinit var binding : ActivitySignupEmailBinding
    private var tab = arrayListOf("전화번호", "이메일")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewPager 연결
        val signUpVPAdapter = SignUpVPAdapter(this)
        binding.signupStep1Vp.adapter = signUpVPAdapter

        // ViewPager와 TabLayout 연결
        TabLayoutMediator(binding.signupStep1Tb, binding.signupStep1Vp) {
                tab, position ->
            tab.text = this.tab[position]
        }.attach()

        binding.signupStep1BackIv.setOnClickListener {
            finish()
        }

        // 로그인 화면 연결
        binding.signupStep1LoginTv.setOnClickListener {
            startLoginActivity()
        }

        binding.signupStep1LoginTv.setOnClickListener {  // 뒤로가기하면 다시 로그인 화면으로
            finish()
        }

    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}