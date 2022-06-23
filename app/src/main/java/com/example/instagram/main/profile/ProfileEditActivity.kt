package com.example.instagram.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.R
import com.example.instagram.data.User
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ActivityProfileEditBinding
import com.example.instagram.room.InstagramDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.util.*
import kotlin.collections.HashMap

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileEditBinding
    private lateinit var instaDB : InstagramDatabase
    private var gson : Gson = Gson()

    // 파이어베이스
    private val database = Firebase.database
    private val storyRef = database.getReference("story")
    private val postRef = database.getReference("post")
    private val userRef = database.getReference("user")

    private lateinit var map: HashMap<String, Objects>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instaDB = InstagramDatabase.getInstance(this)!!

        // 사용자 ID 가져오기
        //UserDB(email=, password=, ID=, name=, picture=0)
        var user = gson.fromJson(getMyInfo(), UserDB::class.java)

        binding.editCancelTv.setOnClickListener {
            finish()
        }

        binding.editUseridEt.setText(user.ID)
        binding.editNameEt.setText(user.name)

        binding.editDoneTv.setOnClickListener {
            var name = binding.editNameEt.text.toString()
            var id = binding.editUseridEt.text.toString()
            editName(name)
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

        val user = gson.fromJson(getMyInfo(), UserDB::class.java)
        user.name = name

        val userEditor = userSP.edit()
        userEditor.apply()

        Log.d("New-name ", user.name)

    }

    // id 편집
    private fun editID(id: String){
        val userSP = getSharedPreferences("user", MODE_PRIVATE)

        val user = gson.fromJson(getMyInfo(), UserDB::class.java)
        user.ID = id

        val userEditor = userSP.edit()
        userEditor.apply()

        Log.d("New-ID ", user.ID)

        saveMyInfo(user)

        userRef.setValue(user)
    }

    private fun getMyInfo(): String {  // 내 정보를 가져오기 위한 함수
        val userSP = this.getSharedPreferences("user", Context.MODE_PRIVATE)

        return userSP.getString("myInfo", "").toString()
    }


    private fun getMyIdx(): Int {  // 내 정보를 가져오기 위한 함수
        val userSP = this.getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getInt("userIdx", 0)
    }

    private fun getMyUid() : String? {  // 내 정보를 가져오기 위한 함수
        val userSP = getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getString("myUid", "")
    }

    private fun saveMyInfo(user : UserDB) {
        val userSP = getSharedPreferences("user", MODE_PRIVATE)
        val userEditor = userSP.edit()

        val userJson = gson.toJson(user)

        userEditor.putString("myInfo", userJson)
        userEditor.apply()
    }
}