package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.R
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ActivitySignupNameBinding
import com.example.instagram.login.LoginActivity
import com.google.gson.Gson

class SignUpNameActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignupNameBinding

    private val gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 화면 연결
        binding.signupNameLoginTv.setOnClickListener {
            startLoginActivity()
        }

        // EditText가 채워지면 다음 버튼 색 변경
        binding.signupNameNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.signupNameNameEt.hint = "입력 시작"
                // 입력이 시작되기 전에 작동
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력이 시작되면 작동
                binding.signupNameNextTv.setBackgroundResource(R.drawable.blue_round_stroke)
            }

            override fun afterTextChanged(p0: Editable?) {
                // 입력이 끝난 후에 작동
                binding.signupNameNameEt.hint = "입력 끝"

            }
        })

        binding.signupNameNextTv.setOnClickListener {
            val name = binding.signupNameNameEt.text.toString()

            if(name.isNotEmpty()) {
                val userSP = getSharedPreferences("user", MODE_PRIVATE)
                var userJson = userSP.getString("userInfo", "")

                val user = gson.fromJson(userJson, UserDB::class.java)
                user.name = name

                val userEditor = userSP.edit()
                userJson = gson.toJson(user)

                userEditor.putString("userInfo", userJson)
                userEditor.apply()

                startActivity(Intent(this, SignUpPasswordActivity::class.java))
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
