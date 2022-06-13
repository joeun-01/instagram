package com.example.instagram.main.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.databinding.ActivityStoryBinding

class StoryActivity : AppCompatActivity() {
    lateinit var binding : ActivityStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storyCloseBtn.setOnClickListener {  // 스토리 닫기 버튼
            finish()
        }

        // 하고 여기에 스토리 내용 초기화해주기
        // 는 나중에~~~~~~~~

    }
}