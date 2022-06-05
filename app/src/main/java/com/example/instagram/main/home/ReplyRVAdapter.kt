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
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.Reply
import com.example.instagram.databinding.ItemReplyBinding
import com.example.instagram.room.InstagramDatabase

class ReplyRVAdapter(context : Context, commentIdx : Int) : RecyclerView.Adapter<ReplyRVAdapter.ViewHolder>() {
    private var instaDB = InstagramDatabase.getInstance(context)!!
    private var profile = instaDB.userDao().getUsers()
    private var reply = instaDB.CommentDao().getCommentReply(commentIdx)

    interface MyItemClickListener{
        // click function
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
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
        fun bind(reply: Reply) {
            // 댓글 내용 표시
            binding.itemReplyProfileIv.setImageResource(instaDB.userDao().getUserPicture(reply.userIdx))

            val replyText = instaDB.userDao().getUserID(reply.userIdx) + " " + reply.comment  // 텍스트 가져옴
            val spannableString = SpannableString(replyText)  //객체 생성

            // 유저 아이디 부분만 두껍게 표시
            val word = instaDB.userDao().getUserID(reply.userIdx)
            val start = replyText.indexOf(word)
            val end = start + word.length

            spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.itemReplyTextTv.text = spannableString

            if(reply.like > 0) {  // 좋아요가 하나라도 되어있으면 좋아요 개수 표시
                binding.itemReplyLikeTv.visibility = View.VISIBLE
            }
        }
    }
}