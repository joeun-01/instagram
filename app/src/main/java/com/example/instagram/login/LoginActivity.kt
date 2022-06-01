package com.example.instagram.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.main.MainActivity
import com.example.instagram.room.InstagramDatabase
import com.example.instagram.signup.SignUpIDActivity

class LoginActivity: AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    lateinit var userDB : InstagramDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDB = InstagramDatabase.getInstance(this)!!

        binding.loginLoginBtn.setOnClickListener {
            login()
        }

        binding.loginSignupTv.setOnClickListener {
            startActivity(Intent(this, SignUpIDActivity::class.java))
        }
    }

    private fun login() {
        val id = binding.loginIdEt.text.toString()
        val password = binding.loginPasswordEt.text.toString()

        if(id.isEmpty()) {
            Toast.makeText(this, "아이디를 입력하세요",Toast.LENGTH_SHORT).show()
            return
        }
        if(password.isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력하세요",Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("입력한 아이디, 비밀번호", "$id, $password")
        Log.d("저장한 정보", userDB.userDao().getUsers().toString())
        Log.d("저장한 정보", userDB.userDao().login(id).toString())

        if(password == userDB.userDao().login(id)) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}