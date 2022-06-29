package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.R
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ActivitySignupIdBinding
import com.example.instagram.login.LoginActivity
import com.google.gson.Gson

class SignUpIDActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignupIdBinding

    private val gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 화면 연결
        binding.signupIdLoginTv.setOnClickListener {
            startLoginActivity()
        }

        // EditText가 채워지면 다음 버튼 색 변경
        binding.signupIDEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.signupIDEt.hint = "입력 시작"
                // 입력이 시작되기 전에 작동
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력이 시작되면 작동
                binding.signupIdNextTv.setBackgroundResource(R.drawable.blue_round_stroke)
            }

            override fun afterTextChanged(p0: Editable?) {
                // 입력이 끝난 후에 작동
                binding.signupIDEt.hint = "입력 끝"

            }
        })

        // 아이디 저장 후 다음 단계로
        binding.signupIdNextTv.setOnClickListener {
            val id = binding.signupIDEt.text.toString()

            if(id.isNotEmpty()) {
                val userSP = getSharedPreferences("user", MODE_PRIVATE)
                var userJson = userSP.getString("userInfo", "")

                val user = gson.fromJson(userJson, UserDB::class.java)
                user.ID = id

                val userEditor = userSP.edit()
                userJson = gson.toJson(user)

                userEditor.putString("userInfo", userJson)
                userEditor.apply()

                startActivity(Intent(this, SignUpCompleteActivity::class.java))
            }
            else {
                Toast.makeText(this, "아이디를 입력해야합니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}