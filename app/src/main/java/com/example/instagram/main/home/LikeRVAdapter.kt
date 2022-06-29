package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.*
import com.example.instagram.databinding.ItemActivityLikeBinding
import com.example.instagram.databinding.ItemCommentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LikeRVAdapter : RecyclerView.Adapter<LikeRVAdapter.ViewHolder>() {
    private var like = arrayListOf<LikedPostDB>()

    // 유저 파이어베이스
    private val database = Firebase.database
    private val userRef = database.getReference("user")

    // 게시물 파이어베이스
    private val postRef = database.getReference("post")

    interface MyItemClickListener{
        // click function
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewLike(like: LikedPostDB) {
        // 리사이클러뷰에 들어갈 좋아요 정보 추가
        this.like.add(like)
        notifyDataSetChanged()
        Log.d("LULLULALLA", this.like.toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearLike() {
        this.like.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemActivityLikeBinding = ItemActivityLikeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 position에 대한 데이터를 binding
        holder.bind(like[position])

        // click listener
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = like.size

    inner class ViewHolder(val binding : ItemActivityLikeBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        @SuppressLint("SetTextI18n")
        fun bind(like: LikedPostDB){
            // 좋아요 단 유저 정보 받아오기
            var user : UserDB
            userRef.child(like.uid).get().addOnSuccessListener {
                user = it.getValue(UserDB::class.java)!!

                // 댓글 내용 표시
                binding.activityLikeProfileIv.setImageResource(user.picture)

                val likeText = user.ID + "님이 회원님의 게시물을 좋아합니다."  // 텍스트 가져옴
                val spannableString = SpannableString(likeText)  //객체 생성

                // 유저 아이디 부분만 두껍게 표시
                val word = user.ID
                val start = likeText.indexOf(word)
                val end = start + word.length

                spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.activityLikeTextTv.text = spannableString

                Log.d("SUCCESS-USER", user.toString())

            }.addOnFailureListener {
                Log.d("FAIL-USER", "유저 데이터를 받아오지 못했습니다")
            }

            // 게시물 사진 받아오기
            var post : PostDB
            postRef.child(like.postIdx.toString()).get().addOnSuccessListener {
                post = it.getValue(PostDB::class.java)!!

                binding.activityLikePostIv.setImageResource(post.picture)
            }
            .addOnFailureListener {
                Log.d("FAIL-POST", "게시물 데이터를 받아오지 못했습니다")
            }

        }
    }
}