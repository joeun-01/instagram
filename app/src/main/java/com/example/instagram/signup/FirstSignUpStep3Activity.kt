package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.data.User
import com.example.instagram.databinding.ActivitySignupPasswordBinding
import com.example.instagram.login.LoginActivity
import com.google.gson.Gson

class FirstSignUpStep3Activity : AppCompatActivity() {

    lateinit var binding : ActivitySignupPasswordBinding

    private val gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 화면 연결
        binding.signupPasswordLoginTv.setOnClickListener {
            startLoginActivity()
        }

        binding.signupPasswordNextBtn.setOnClickListener {
            val password = binding.signupPasswordEt.text.toString()

            if(password.isNotEmpty()) {
                val userSP = getSharedPreferences("user", MODE_PRIVATE)
                var userJson = userSP.getString("userInfo", "")

                val user = gson.fromJson(userJson, User::class.java)
                user.password = password

                val userEditor = userSP.edit()
                userJson = gson.toJson(user)

                userEditor.putString("userInfo", userJson)
                userEditor.apply()

                startActivity(Intent(this, FirstSignUpStep4Activity::class.java))
            }
            else {
                Toast.makeText(this, "비밀번호를 입력해야합니다.", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}