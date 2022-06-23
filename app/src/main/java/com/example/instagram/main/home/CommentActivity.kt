package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.data.CommentDB
import com.example.instagram.data.Post
import com.example.instagram.data.PostDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ActivityCommentBinding
import com.example.instagram.room.InstagramDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class CommentActivity : AppCompatActivity() {
    lateinit var binding : ActivityCommentBinding

    private val gson : Gson = Gson()
    lateinit var post : PostDB

    // 댓글 파이어베이스
    private val database = Firebase.database
    private val commentRef = database.getReference("comment")

    // 유저 파이어베이스
    private val userRef = database.getReference("user")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.commentBackIv.setOnClickListener {
            finish()
        }

        // 게시물 데이터 받아오기
        val postSP = getSharedPreferences("post", MODE_PRIVATE)
        val postJson = postSP.getString("postInfo", "")

        post = gson.fromJson(postJson, PostDB::class.java)

        // 게시물을 올린 유저 정보 받아오기
        var user : UserDB

        userRef.child(post.uid!!).get().addOnSuccessListener {
            user = it.getValue(UserDB::class.java)!!

            // 게시물과 프로필 사진 연동
            binding.commentProfileIv.setImageResource(user.picture)

            // 게시물 글 연동
            val commentText = user.ID + " " + post.text  // 텍스트 가져옴
            val spannableString = SpannableString(commentText)  //객체 생성

            // 유저 아이디 부분만 두껍게 표시
            val word = user.ID
            val start = commentText.indexOf(word)
            val end = start + word.length

            spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.commentTextTv.text = spannableString

            Log.d("SUCCESS-USER", user.toString())

        }.addOnFailureListener {
            Log.d("FAIL-STORY", "유저 데이터를 받아오지 못했습니다")
        }

        val commentRVAdapter = CommentRVAdapter(this)
        binding.commentReplyRv.adapter = commentRVAdapter
        binding.commentReplyRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 게시물에 달린 댓글 받아오기
        commentRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                commentRVAdapter.clearNewComment()

                if (snapshot.exists()){
                    for (commentSnapShot in snapshot.children){
                        val getData = commentSnapShot.getValue(CommentDB::class.java)

                        if (getData != null) {
                            if(getData.postIdx == post.postIdx)
                            commentRVAdapter.addNewComment(getData)
                        }

                        Log.d("SUCCESS-COM", getData.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FAIL", "데이터를 불러오지 못했습니다")
            }
        })
    }
}