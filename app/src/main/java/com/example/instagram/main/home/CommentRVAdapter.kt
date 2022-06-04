package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.Comment
import com.example.instagram.databinding.ItemCommentBinding
import com.example.instagram.room.InstagramDatabase

class CommentRVAdapter(context : Context, postIdx : Int) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {
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
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = comment.size

    inner class ViewHolder(private val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        @SuppressLint("SetTextI18n")
        fun bind(comment: Comment){
            binding.itemCommentProfileIv.setImageResource(instaDB.userDao().getUserPicture(comment.userIdx))

            binding.itemCommentTextTv.text = instaDB.userDao().getUserID(comment.userIdx) + " " + comment.comment

            if(comment.like > 0) {  // 좋아요가 하나라도 되어있으면 좋아요 개수 표시
                binding.itemCommentLikeTv.visibility = View.VISIBLE
            }
        }
    }
}