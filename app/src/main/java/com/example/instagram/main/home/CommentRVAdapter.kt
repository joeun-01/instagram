package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.Comment
import com.example.instagram.databinding.ItemCommentBinding
import com.example.instagram.room.InstagramDatabase

class CommentRVAdapter(private val context : Context, postIdx : Int) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {
    private var instaDB = InstagramDatabase.getInstance(context)!!
    private var profile = instaDB.userDao().getUsers()
    private var comment = instaDB.CommentDao().getPostComments(postIdx)

    interface MyItemClickListener{
        // click function
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 position에 대한 데이터를 binding
        holder.bind(comment[position])

        // click listener
        holder.binding.itemCommentShowReply.setOnClickListener {
            holder.binding.itemCommentReplyRv.visibility = View.VISIBLE
            holder.binding.itemCommentShowReply.visibility = View.GONE
        }
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = comment.size

    inner class ViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        @SuppressLint("SetTextI18n")
        fun bind(comment: Comment){
            // 댓글 내용 표시
            binding.itemCommentProfileIv.setImageResource(instaDB.userDao().getUserPicture(comment.userIdx))

            val commentText = instaDB.userDao().getUserID(comment.userIdx) + " " + comment.comment  // 텍스트 가져옴
            val spannableString = SpannableString(commentText)  //객체 생성

            // 유저 아이디 부분만 두껍게 표시
            val word = instaDB.userDao().getUserID(comment.userIdx)
            val start = commentText.indexOf(word)
            val end = start + word.length

            spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.itemCommentTextTv.text = spannableString

            if(comment.like > 0) {  // 좋아요가 하나라도 되어있으면 좋아요 개수 표시
                binding.itemCommentLikeTv.visibility = View.VISIBLE
            }

            binding.itemCommentShowReplyTv.text = "답글 " + instaDB.CommentDao().getCommentReply(comment.commentIdx).size + "개 모두 보기"

            // 답글 리사이클러뷰 연결
            val replyRVAdapter = ReplyRVAdapter(context, comment.commentIdx)
            binding.itemCommentReplyRv.adapter = replyRVAdapter
            binding.itemCommentReplyRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            // 답글이 없으면 더보기를 안띄우는 게 기본
            if(instaDB.CommentDao().getCommentReply(comment.commentIdx).size == 1) {
                // 답글이 하나면 그냥 보여주기
                binding.itemCommentReplyRv.visibility = View.VISIBLE
            }
            else if(instaDB.CommentDao().getCommentReply(comment.commentIdx).size > 1) {
                // 답글이 두 개 이상이면 더보기를 띄워주기
                binding.itemCommentShowReply.visibility = View.VISIBLE
            }

        }
    }
}