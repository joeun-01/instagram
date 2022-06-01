package com.example.instagram.signup

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDB = InstagramDatabase.getInstance(this)!!

        binding.signupCompleteTv.setOnClickListener {
            val userSP = getSharedPreferences("user", MODE_PRIVATE)
            var userJson = userSP.getString("userInfo", "")

            val user = gson.fromJson(userJson, User::class.java)
            userDB.userDao().insert(user)

            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}