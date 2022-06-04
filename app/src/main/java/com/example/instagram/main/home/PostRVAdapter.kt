package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.data.Comment
import com.example.instagram.data.Post
import com.example.instagram.databinding.ItemPostBinding
import com.example.instagram.room.InstagramDatabase

class PostRVAdapter(context : Context, private val myIdx : Int) : RecyclerView.Adapter<PostRVAdapter.ViewHolder>() {
    private val instaDB = InstagramDatabase.getInstance(context)!!

    private val post = instaDB.postDao().getPosts()
    private var comment = ArrayList<Comment>()

    interface MyItemClickListener{
        fun showComment(postIdx : Int)
        fun writeComment(postIdx : Int)
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
        // 게시물에 있는 댓글 불러오기
        comment.clear()
        comment.addAll(instaDB.CommentDao().getPostComments(post[position].postIdx))

        // 해당 position에 대한 데이터를 binding
        holder.bind(post[position])

        // click listener
        holder.binding.itemPostLikeIv.setOnClickListener {
            var isLike = instaDB.postDao().getLikeByID(post[position].userIdx)
            updateLike(isLike, post[position].userIdx)

            if(isLike) {
                holder.binding.itemPostLikeIv.setImageResource(R.drawable.ic_heart)
            }
            else {
                holder.binding.itemPostLikeIv.setImageResource(R.drawable.ic_filled_heart)
            }
        }

        holder.binding.itemPostShowAllCommentTv.setOnClickListener {
            mItemClickListener.showComment(post[position].postIdx)
        }

        holder.binding.itemPostCommentTv.setOnClickListener {
            mItemClickListener.writeComment(post[position].postIdx)
        }
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = post.size

    inner class ViewHolder(val binding : ItemPostBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        @SuppressLint("SetTextI18n")
        fun bind(post: Post){
            val user = instaDB.userDao().getUser(post.userIdx)

            Log.d("유저 정보", instaDB.userDao().getUsers().toString())

            binding.itemPostIdTv.text = user.ID
            binding.itemPostProfileIv.setImageResource(user.picture)

            binding.itemPostIv.setImageResource(post.picture)
            binding.itemPostTextTv.text = post.text

            binding.itemPostShowAllCommentTv.text = "댓글 " + comment.size + "개 모두 보기"
            binding.itemPostMyProfileIv.setImageResource(instaDB.userDao().getUserPicture(myIdx))
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