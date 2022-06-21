package com.example.instagram.main.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ActivityStoryBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class StoryActivity : AppCompatActivity() {

    lateinit var binding : ActivityStoryBinding

    private val gson : Gson = Gson()

    private val database = Firebase.database
    private val userRef = database.getReference("user")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // intent로 넘겨받은 스토리 주인 userIdx 가져오기
        val userID = intent.getStringExtra("userID")

        // 스토리 정보 불러오기
        var story = intent.getIntExtra("story", 0)
        binding.storyImgIv.setImageResource(story)

        // 유저 정보 불러오기
        var user : UserDB
        userRef.child(userID!!).get().addOnSuccessListener {
            user = it.getValue(UserDB::class.java)!!

            // 유저 정보 연동
            binding.storyProfileIv.setImageResource(user.picture)
            binding.storyNameTv.text = user.ID

            Log.d("SUCCESS-STORY", user.toString())

        }.addOnFailureListener {
            Log.d("FAIL-STORY", "스토리 데이터를 받아오지 못했습니다")
        }

        binding.storyCloseBtn.setOnClickListener {  // 스토리 닫기 버튼
            finish()
        }
    }

    private fun getMyUid() : String? {  // 내 정보를 가져오기 위한 함수
        val userSP = getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getString("myUid", "")
    }

    private fun getMyInfo() : UserDB? {  // 내 정보를 가져오기 위한 함수
        val userSP = getSharedPreferences("user", MODE_PRIVATE)

        val userJson = userSP.getString("myInfo", "")

        return gson.fromJson(userJson, UserDB::class.java)
    }
}