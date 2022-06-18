package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.data.User
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ActivityFirstSignupStep2Binding
import com.example.instagram.login.LoginActivity
import com.google.gson.Gson

class FirstSignUpStep2Activity : AppCompatActivity() {

    lateinit var binding : ActivityFirstSignupStep2Binding

    private val gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstSignupStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 화면 연결
        binding.firstSignupStep2LoginTv.setOnClickListener {
            startLoginActivity()
        }

        binding.firstSignupStep2NextTv.setOnClickListener {
            val name = binding.firstSignupStep2NameEt.text.toString()

            if(name.isNotEmpty()) {
                val userSP = getSharedPreferences("user", MODE_PRIVATE)
                var userJson = userSP.getString("userInfo", "")

                val user = gson.fromJson(userJson, UserDB::class.java)
                user.name = name

                val userEditor = userSP.edit()
                userJson = gson.toJson(user)

                userEditor.putString("userInfo", userJson)
                userEditor.apply()

                startActivity(Intent(this, FirstSignUpStep3Activity::class.java))
            }
            else {
                Toast.makeText(this, "이름을 입력해야합니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}