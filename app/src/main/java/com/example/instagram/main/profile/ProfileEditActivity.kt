package com.example.instagram.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.databinding.ActivityProfileEditBinding
import com.example.instagram.main.MainActivity
import com.example.instagram.room.InstagramDatabase

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileEditBinding
    private lateinit var instaDB : InstagramDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instaDB = InstagramDatabase.getInstance(this)!!

        binding.editCancelTv.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        //binding.editNameEt.text= instaDB.userDao().getUserID(getMyIdx())

    }

    private fun getMyIdx(): Int {  // 내 정보를 가져오기 위한 함수
        val userSP = this.getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getInt("userIdx", 0)
    }
}