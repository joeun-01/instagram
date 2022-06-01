package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.data.User
import com.example.instagram.databinding.ActivitySignupPasswordBinding
import com.google.gson.Gson

class SignUpPasswordActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignupPasswordBinding

    private val gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupPasswordBackIv.setOnClickListener {
            // 뒤로가기 버튼 누르면 아이디 입력 창으로 다시 돌아감
            startActivity(Intent(this, SignUpIDActivity::class.java))
        }

        binding.signupPasswordNextBtn.setOnClickListener {  // 비밀번호 입력 후 넘어가기
            var password = binding.signupPasswordEt.text.toString()

            if(password.isNotEmpty()) {
                val userSP = getSharedPreferences("user", MODE_PRIVATE)
                var userJson = userSP.getString("userInfo", "")

                val user = gson.fromJson(userJson, User::class.java)
                user.password = password

                val userEditor = userSP.edit()
                userJson = gson.toJson(user)

                userEditor.putString("userInfo", userJson)
                userEditor.apply()

                startActivity(Intent(this, SignUpCompleteActivity::class.java))
            }
            else {
                Toast.makeText(this, "비밀번호를 입력해야합니다.", Toast.LENGTH_SHORT)
            }
        }
    }
}