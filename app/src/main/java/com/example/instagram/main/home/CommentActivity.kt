package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.data.Post
import com.example.instagram.databinding.ActivityCommentBinding
import com.example.instagram.room.InstagramDatabase

class CommentActivity : AppCompatActivity() {
    lateinit var binding : ActivityCommentBinding

    private lateinit var instaDB : InstagramDatabase
    lateinit var post : Post

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instaDB = InstagramDatabase.getInstance(this)!!

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.commentBackIv.setOnClickListener {
            finish()
        }

        val postSP = getSharedPreferences("post", MODE_PRIVATE)
        val postIdx = postSP.getInt("postIdx", 0)

        post = instaDB.postDao().getPost(postIdx)

        // 게시물과 프로필 사진 연동
        binding.commentProfileIv.setImageResource(instaDB.userDao().getUserPicture(post.userIdx))

        // 게시물 글 연동
        val id = instaDB.userDao().getUserID(post.userIdx)
        val text = post.text

        binding.commentTextTv.text = "$id $text"

        val commentRVAdapter = CommentRVAdapter(this, postIdx)
        binding.commentReplyRv.adapter = commentRVAdapter
        binding.commentReplyRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}