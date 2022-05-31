package com.example.instagram.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.data.Post
import com.example.instagram.data.User
import com.example.instagram.databinding.ItemHomePostBinding
import com.example.instagram.databinding.ItemHomeStoryBinding
import com.example.instagram.room.InstagramDatabase

class PostRVAdapter(context : Context, private var post: ArrayList<Post>) : RecyclerView.Adapter<PostRVAdapter.ViewHolder>() {

    private val instaDB = InstagramDatabase.getInstance(context)!!

    interface MyItemClickListener{

    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    private fun updateLike(isLike: Boolean, userID :String) {
        instaDB.postDao().updateLikeByID(!isLike, userID)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemHomePostBinding = ItemHomePostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 position에 대한 데이터를 binding
        holder.bind(post[position])

        // click listener
        holder.binding.itemHomePostLikeIv.setOnClickListener {
            var isLike = instaDB.postDao().getLikeByID(post[position].userID)
            updateLike(isLike, post[position].userID)

            if(isLike) {
                holder.binding.itemHomePostLikeIv.setImageResource(R.drawable.ic_heart)
            }
            else {
                holder.binding.itemHomePostLikeIv.setImageResource(R.drawable.ic_filled_heart)
            }
        }
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = post.size

    inner class ViewHolder(val binding : ItemHomePostBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        fun bind(post: Post){
            binding.itemHomePostIdTv.text = post.userID
            binding.itemHomePostProfileIv.setImageResource(post.picture)

            binding.itemHomePostIv.setImageResource(post.picture)
            binding.itemHomeCommentTv.text = post.context

            if(instaDB.postDao().getLikeByID(post.userID)) {
                binding.itemHomePostLikeIv.setImageResource(R.drawable.ic_heart)
            }
            else {
                binding.itemHomePostLikeIv.setImageResource(R.drawable.ic_filled_heart)
            }
        }
    }
}