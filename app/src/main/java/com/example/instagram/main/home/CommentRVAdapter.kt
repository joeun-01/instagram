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
import com.example.instagram.data.CommentDB
import com.example.instagram.data.ReplyDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ItemCommentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CommentRVAdapter(private var context: Context) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {
    private var comment = arrayListOf<CommentDB>()

    // 유저 파이어베이스
    private val database = Firebase.database
    private val userRef = database.getReference("user")

    // 답글 파이어베이스
    private val replyRef = database.getReference("reply")

    interface MyItemClickListener{
        // click function
        fun onWriteReply(comment: CommentDB)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewComment(comment: CommentDB) {
        // 리사이클러뷰에 들어갈 게시물 추가
        this.comment.add(comment)
        notifyDataSetChanged()
        Log.d("SUCCESS-POST", this.comment.toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearNewComment() {
        this.comment.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 position에 대한 데이터를 binding
        holder.bind(comment[position])

        // click listener

        // 답글 보기
        holder.binding.itemCommentShowReply.setOnClickListener {
            holder.binding.itemCommentReplyRv.visibility = View.VISIBLE
            holder.binding.itemCommentShowReply.visibility = View.GONE
            holder.binding.itemComment.setPadding(0, 0, 0, 10)
        }

        // 답글 달기
        holder.binding.itemCommentReplyTv.setOnClickListener {
            mItemClickListener.onWriteReply(comment[position])
        }
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = comment.size

    inner class ViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        @SuppressLint("SetTextI18n")
        fun bind(comment: CommentDB){
            // 댓글 단 유저 정보 받아오기
            var user : UserDB

            userRef.child(comment.uid).get().addOnSuccessListener {
                user = it.getValue(UserDB::class.java)!!

                // 댓글 내용 표시
                binding.itemCommentProfileIv.setImageResource(user.picture)

                val commentText = user.ID + "  " + comment.comment  // 텍스트 가져옴
                val spannableString = SpannableString(commentText)  //객체 생성

                // 유저 아이디 부분만 두껍게 표시
                val word = user.ID
                val start = commentText.indexOf(word)
                val end = start + word.length

                spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.itemCommentTextTv.text = spannableString

                Log.d("SUCCESS-USER", user.toString())

            }.addOnFailureListener {
                Log.d("FAIL-STORY", "유저 데이터를 받아오지 못했습니다")
            }

            if(comment.like > 0) {  // 좋아요가 하나라도 되어있으면 좋아요 개수 표시
                binding.itemCommentLikeTv.visibility = View.VISIBLE
            }

            // 답글 리사이클러뷰 연결
            val replyRVAdapter = ReplyRVAdapter()
            binding.itemCommentReplyRv.adapter = replyRVAdapter
            binding.itemCommentReplyRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            // 답글 불러오기
            var replyCount = 0

            replyRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    replyRVAdapter.clearNewReply()

                    if (snapshot.exists()){
                        for (replySnapShot in snapshot.children){
                            val getData = replySnapShot.getValue(ReplyDB::class.java)

                            if (getData != null) {
                                if(getData.commentIdx == comment.commentIdx){
                                    replyRVAdapter.addNewReply(getData)
                                    replyCount++
                                    Log.d("SUCCESS-RR", getData.toString())
                                }
                            }
                        }

//                        Log.d("CHECK", replyCount.toString())

                        binding.itemCommentShowReplyTv.text = "답글 " + replyRVAdapter.itemCount + "개 모두 보기"

                        // 답글이 없으면 더보기를 안띄우는 게 기본
                        if(replyCount == 1) {
                            // 답글이 하나면 그냥 보여주기
                            binding.itemCommentReplyRv.visibility = View.VISIBLE
                        }
                        else if(replyCount > 1) {
                            // 답글이 두 개 이상이면 더보기를 띄워주기
                            binding.itemCommentShowReply.visibility = View.VISIBLE
                            binding.itemCommentReplyRv.visibility = View.GONE
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("FAIL", "데이터를 불러오지 못했습니다")
                }
            })

        }
    }
}