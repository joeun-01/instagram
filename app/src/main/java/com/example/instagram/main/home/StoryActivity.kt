package com.example.instagram.main.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.databinding.ActivityStoryBinding
import com.example.instagram.room.InstagramDatabase

class StoryActivity : AppCompatActivity() {

    lateinit var binding : ActivityStoryBinding

    lateinit var instaDB : InstagramDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instaDB = InstagramDatabase.getInstance(this)!!

        // intent로 넘겨받은 스토리 주인 userIdx 가져오기
        val userIdx = intent.getIntExtra("userIdx", 0)
        val story = instaDB.storyDao().getUserStory(userIdx)

        binding.storyCloseBtn.setOnClickListener {  // 스토리 닫기 버튼
            finish()
        }

        // 스토리 내용 초기화해주기
        binding.storyProfileIv.setImageResource(instaDB.userDao().getUserPicture(userIdx))
        binding.storyNameTv.text = instaDB.userDao().getUserID(userIdx)
        binding.storyImgIv.setImageResource(story.picture)

    }

    private fun getMyIdx(): Int {  // 내 정보를 가져오기 위한 함수
        val userSP = getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getInt("userIdx", 0)
    }
}