package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.ReplyDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ItemReplyBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ReplyRVAdapter : RecyclerView.Adapter<ReplyRVAdapter.ViewHolder>() {
    // 유저 파이어베이스
    private val database = Firebase.database
    private val userRef = database.getReference("user")

    private var reply = arrayListOf<ReplyDB>()

    interface MyItemClickListener{
        // click function
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewReply(reply: ReplyDB) {
        // 리사이클러뷰에 들어갈 게시물 추가
        this.reply.add(reply)
        notifyDataSetChanged()
        Log.d("SUCCESS-REPLY", this.reply.toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearNewReply() {
        this.reply.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemReplyBinding = ItemReplyBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 position에 대한 데이터를 binding
        holder.bind(reply[position])

        // click listener
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = reply.size

    inner class ViewHolder(private val binding : ItemReplyBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        @SuppressLint("SetTextI18n")
        fun bind(reply: ReplyDB) {
            // 댓글 단 유저 정보 받아오기
            var user : UserDB

            userRef.child(reply.uid).get().addOnSuccessListener {
                user = it.getValue(UserDB::class.java)!!

                // 댓글 내용 표시
                binding.itemReplyProfileIv.setImageResource(user.picture)

                val replyText = user.ID + " " + reply.comment  // 텍스트 가져옴
                val spannableString = SpannableString(replyText)  //객체 생성

                // 유저 아이디 부분만 두껍게 표시
                val word = user.ID
                val start = replyText.indexOf(word)
                val end = start + word.length

                spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.itemReplyTextTv.text = spannableString

                Log.d("SUCCESS-USER", user.toString())

            }.addOnFailureListener {
                Log.d("FAIL-REPLY", "유저 데이터를 받아오지 못했습니다")
            }

            if(reply.like > 0) {  // 좋아요가 하나라도 되어있으면 좋아요 개수 표시
                binding.itemReplyLikeTv.visibility = View.VISIBLE
            }
        }
    }
}