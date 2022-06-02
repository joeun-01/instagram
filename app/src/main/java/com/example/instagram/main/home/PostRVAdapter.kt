package com.example.instagram.main.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.data.Post
import com.example.instagram.databinding.ItemPostBinding
import com.example.instagram.room.InstagramDatabase

class PostRVAdapter(context : Context) : RecyclerView.Adapter<PostRVAdapter.ViewHolder>() {
    private val instaDB = InstagramDatabase.getInstance(context)!!
    private val post = instaDB.postDao().getPosts()

    interface MyItemClickListener{

    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    private fun updateLike(isLike: Boolean, userIdx :Int) {
        instaDB.postDao().updateLikeByID(!isLike, userIdx)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 position에 대한 데이터를 binding
        holder.bind(post[position])

        // click listener
        holder.binding.itemHomePostLikeIv.setOnClickListener {
            var isLike = instaDB.postDao().getLikeByID(post[position].userIdx)
            updateLike(isLike, post[position].userIdx)

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

    inner class ViewHolder(val binding : ItemPostBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        fun bind(post: Post){
            val user = instaDB.userDao().getUser(post.userIdx)

            Log.d("유저 정보", instaDB.userDao().getUsers().toString())

            binding.itemHomePostIdTv.text = user.ID
            binding.itemHomePostProfileIv.setImageResource(user.picture)

            binding.itemHomePostIv.setImageResource(post.picture)
            binding.itemHomePostCommentTv.text = post.context
//
//            if(instaDB.postDao().getLikeByID(post.userIdx)) {
//                binding.itemHomePostLikeIv.userIdx(R.drawable.ic_heart)
//            }
//            else {
//                binding.itemHomePostLikeIv.setImageResource(R.drawable.ic_filled_heart)
//            }
        }
    }
}