package com.example.instagram.main.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {
    lateinit var binding : ActivityCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.commentBackIv.setOnClickListener {
            finish()
        }

        val commentRVAdapter = CommentRVAdapter(this)
        binding.commentCommentRv.adapter = commentRVAdapter
        binding.commentCommentRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}