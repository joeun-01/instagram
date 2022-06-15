package com.example.instagram.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.R
import com.example.instagram.data.User
import com.example.instagram.databinding.ActivityProfileEditBinding
import com.example.instagram.room.InstagramDatabase
import com.google.gson.Gson

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileEditBinding
    private lateinit var instaDB : InstagramDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instaDB = InstagramDatabase.getInstance(this)!!

        binding.editCancelTv.setOnClickListener {
            finish()
        }

        binding.editUseridEt.setText(instaDB.userDao().getUserID(getMyIdx()))
        binding.editNameEt.setText(instaDB.userDao().getUserName(getMyIdx()))

        binding.editDoneTv.setOnClickListener {
            var name = binding.editNameEt.text.toString()
            var id = binding.editUseridEt.text.toString()
            if (name.isNotEmpty()){
                editName(name)
            }
            if (id.isNotEmpty()){
                editID(id)
            }

            Log.d("edit User name ", instaDB.userDao().getUser(getMyIdx()).toString())

            finish()
        }

    }

    // 이름 편집
    private fun editName(name: String){
        val userSP = getSharedPreferences("user", MODE_PRIVATE)

        val user = instaDB.userDao().getUser(getMyIdx())
        user.name = name

        val userEditor = userSP.edit()
        userEditor.apply()

        instaDB.userDao().update(user)
    }

    // id 편집
    private fun editID(id: String){
        val userSP = getSharedPreferences("user", MODE_PRIVATE)

        val user = instaDB.userDao().getUser(getMyIdx())
        user.ID = id

        val userEditor = userSP.edit()
        userEditor.apply()

        instaDB.userDao().update(user)
    }


    private fun getMyIdx(): Int {  // 내 정보를 가져오기 위한 함수
        val userSP = this.getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getInt("userIdx", 0)
    }
}