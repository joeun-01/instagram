package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
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

        val commentText = instaDB.userDao().getUserID(post.userIdx) + " " + post.text  // 텍스트 가져옴
        val spannableString = SpannableString(commentText)  //객체 생성

        // 유저 아이디 부분만 두껍게 표시
        val word = instaDB.userDao().getUserID(post.userIdx)
        val start = commentText.indexOf(word)
        val end = start + word.length

        spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.commentTextTv.text = spannableString

        val commentRVAdapter = CommentRVAdapter(this, postIdx)
        binding.commentReplyRv.adapter = commentRVAdapter
        binding.commentReplyRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        // 왜 자꾸 behind 라고 뜨는 거지 ???????????????????????????

    }
}