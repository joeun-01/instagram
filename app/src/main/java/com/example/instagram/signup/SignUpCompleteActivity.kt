package com.example.instagram.signup

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.data.User
import com.example.instagram.databinding.ActivitySignupCompleteBinding
import com.example.instagram.databinding.ActivitySignupPasswordBinding
import com.example.instagram.login.LoginActivity
import com.example.instagram.room.InstagramDatabase
import com.google.gson.Gson

class SignUpCompleteActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignupCompleteBinding

    private val gson : Gson = Gson()
    lateinit var userDB : InstagramDatabase

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDB = InstagramDatabase.getInstance(this)!!

        // 여태까지 저장한 유저 정보 가져오기
        val userSP = getSharedPreferences("user", MODE_PRIVATE)
        var userJson = userSP.getString("userInfo", "")

        val user = gson.fromJson(userJson, User::class.java)

        binding.signupCompleteWelcomeTv.text = user.ID + "님, Instagram에\n 오신 것을 환영합니다"

        binding.signupCompleteCompleteBtn.setOnClickListener {

            user.userIdx = userDB.userDao().getUsers().size + 1
            userDB.userDao().insert(user)

            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}