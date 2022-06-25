package com.example.instagram.main.home

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.example.instagram.data.CommentDB
import com.example.instagram.data.CountDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.WriteReplyBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class CommentBottomSheetDialog : BottomSheetDialogFragment() {
    lateinit var binding : WriteReplyBottomSheetDialogBinding

    private val gson : Gson = Gson()

    // 파이어베이스
    private lateinit var mDatabase : DatabaseReference
    private val database = Firebase.database
    private val countRef = database.getReference("count")
    private var commentNum = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WriteReplyBottomSheetDialogBinding.inflate(inflater, container, false)

        mDatabase = FirebaseDatabase.getInstance().reference  // 데이터 넣기용

        val commentSP = requireActivity().getSharedPreferences("post", MODE_PRIVATE)
        val postIdx = commentSP.getInt("post_comment", 0)

        // 내 프사 달기
        binding.writeCommentProfileIv.setImageResource(getMyInfo()!!.picture)

        // 다이얼로그를 올리면서 키보드도 같이 올려주기
//        val inputManager : InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        inputManager.showSoftInput(binding.writeCommentEt, InputMethodManager.SHOW_IMPLICIT)

        // 댓글 달기
        binding.writeCommentEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.writeCommentEt.hint = "댓글 달기 시작"
                // 입력이 시작되기 전에 작동
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력이 시작되면 작동
                binding.writeCommentEnterTv.setTextColor(Color.parseColor("#0C88FA"))
            }

            override fun afterTextChanged(p0: Editable?) {
                // 입력이 끝난 후에 작동
                binding.writeCommentEt.hint = "댓글 달기..."

            }
        })

        binding.writeCommentEnterTv.setOnClickListener {
            // 데이터베이스에 댓글 저장
            putIntoDatabase(postIdx, binding.writeCommentEt.text.toString())

            // 댓글 달기 창 원래대로 되돌리기
            binding.writeCommentEt.text = null
            binding.writeCommentEnterTv.setTextColor(Color.parseColor("#A2CFF9"))
        }

        return binding.root
    }

    private fun getMyUid() : String? {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getString("myUid", "")
    }
    private fun getMyInfo() : UserDB? {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", MODE_PRIVATE)

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