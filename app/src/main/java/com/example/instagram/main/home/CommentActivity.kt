package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.data.CommentDB
import com.example.instagram.data.CountDB
import com.example.instagram.data.PostDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ActivityCommentBinding
import com.google.firebase.database.*
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

    // 댓글 달기 파이어베이스
    private lateinit var mDatabase : DatabaseReference
    private val countRef = database.getReference("count")
    private var commentNum = 0

    // 유저 파이어베이스
    private val userRef = database.getReference("user")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDatabase = FirebaseDatabase.getInstance().reference  // 데이터 넣기용

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

        // 내 프사 달기
        binding.commentMyProfileIv.setImageResource(getMyInfo()!!.picture)


        // 댓글 달기
        binding.commentWriteEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.commentWriteEt.hint = "댓글 달기 시작"
                // 입력이 시작되기 전에 작동
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력이 시작되면 작동
                binding.commentEnterTv.setTextColor(Color.parseColor("#0C88FA"))
            }

            override fun afterTextChanged(p0: Editable?) {
                // 입력이 끝난 후에 작동
                binding.commentWriteEt.hint = "댓글 달기..."

            }
        })

        binding.commentEnterTv.setOnClickListener {
            if(binding.commentWriteEt.text != null) {
                // 데이터베이스에 댓글 저장
                putIntoDatabase(post.postIdx, binding.commentWriteEt.text.toString())

                // 댓글 달기 창 원래대로 되돌리기
                binding.commentWriteEt.text = null
                binding.commentEnterTv.setTextColor(Color.parseColor("#A2CFF9"))

                // 키보드 내리기
                val inputManager : InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(getCurrentFocus()!!.getWindowToken()!!, InputMethodManager.HIDE_NOT_ALWAYS)
            }
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

    private fun putIntoDatabase(postIdx : Int, comment : String) {
        countRef.get().addOnSuccessListener {
            val count = it.getValue(CountDB::class.java)!!

            commentNum = count.comment + 1  // 댓글 인덱스 가지고 온 후 업데이트
            mDatabase.child("count").setValue(CountDB(count.user, count.story, count.post, count.comment + 1, count.reply))

            mDatabase.child("comment").child(commentNum.toString()).setValue(CommentDB(getMyUid()!!, postIdx, commentNum, comment, "", 0))


        }.addOnFailureListener {
            Log.d("FAIL-STORY", "유저 데이터를 받아오지 못했습니다")
        }
    }
}