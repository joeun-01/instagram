package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.R
import com.example.instagram.data.User
import com.example.instagram.databinding.ActivitySignupIdBinding
import com.example.instagram.login.LoginActivity
import com.google.gson.Gson

class SignUpIDActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignupIdBinding

    private var gson : Gson = Gson()
    private var user = User("", "", R.drawable.ic_filled_heart, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupIdBackIv.setOnClickListener {
            // 엑스 버튼을 누르면 다시 로그인 화면으로 돌아감
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupIdNextBtn.setOnClickListener {  // 아이디 입력 후 넘어가기
            var id = binding.signupIdEt.text.toString()

            if(id.isNotEmpty()) {
                val userSP = getSharedPreferences("user", MODE_PRIVATE)
                val userEditor = userSP.edit()

                user.ID = id

                val userJson = gson.toJson(user)
                userEditor.putString("userInfo", userJson)

                userEditor.apply()

                startActivity(Intent(this, SignUpPasswordActivity::class.java))
            }
            else {
                Toast.makeText(this, "ID를 입력해야합니다.", Toast.LENGTH_SHORT)
            }
        }
    }
}