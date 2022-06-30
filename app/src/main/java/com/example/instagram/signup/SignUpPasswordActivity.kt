package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.R
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ActivitySignupPasswordBinding
import com.example.instagram.login.LoginActivity
import com.google.gson.Gson

class SignUpPasswordActivity : AppCompatActivity() {

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

        // EditText가 채워지면 다음 버튼 색 변경
        binding.signupPasswordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.signupPasswordEt.hint = "입력 시작"
                // 입력이 시작되기 전에 작동
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력이 시작되면 작동
                binding.signupPasswordEt.hint = "입력 중"

            }

            override fun afterTextChanged(text: Editable?) {
                // 입력이 끝난 후에 작동
                if (text != null) {  // 무조건 6글자 이상이어야 함
                    if(text.length >= 6) {
                        binding.signupPasswordNextBtn.setBackgroundResource(R.drawable.blue_round_stroke)
                    }
                    else{
                        binding.signupPasswordNextBtn.setBackgroundResource(R.drawable.lightblue_round_stroke)
                    }
                }

            }
        })

        // 비밀번호 저장 후 다음 단계로 넘어가기
        binding.signupPasswordNextBtn.setOnClickListener {
            val password = binding.signupPasswordEt.text.toString()

            if(password.isNotEmpty()) {
                val userSP = getSharedPreferences("user", MODE_PRIVATE)
                var userJson = userSP.getString("userInfo", "")

                val user = gson.fromJson(userJson, UserDB::class.java)
                user.password = password

                val userEditor = userSP.edit()
                userJson = gson.toJson(user)

                userEditor.putString("userInfo", userJson)
                userEditor.apply()

                startActivity(Intent(this, SignUpBirthdayActivity::class.java))
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