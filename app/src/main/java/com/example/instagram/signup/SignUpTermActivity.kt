package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.R
import com.example.instagram.databinding.ActivitySignupTermsBinding

class SignUpTermActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignupTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(binding.signupTermAgreeAllCb.isChecked) {
            binding.signupTermUseCb.isChecked = true
            binding.signupTermDataCb.isChecked = true
            binding.signupTermLocationCb.isChecked = true
        }

        if(binding.signupTermAgreeAllCb.isChecked&& binding.signupTermUseCb.isChecked
            && binding.signupTermDataCb.isChecked && binding.signupTermLocationCb.isChecked) {
            binding.signupTermNextBtn.setBackgroundResource(R.drawable.blue_round_stroke)
        }

        // 다음 단계로 넘어가기
        binding.signupTermNextBtn.setOnClickListener {
            startActivity(Intent(this, SignUpIDActivity::class.java))
        }
    }
}