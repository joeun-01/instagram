package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.data.Comment
import com.example.instagram.data.LikedPost
import com.example.instagram.data.Post
import com.example.instagram.data.Reply
import com.example.instagram.databinding.ItemPostBinding
import com.example.instagram.room.InstagramDatabase

class PostRVAdapter(context : Context, private val myIdx : Int) : RecyclerView.Adapter<PostRVAdapter.ViewHolder>() {
    private val instaDB = InstagramDatabase.getInstance(context)!!

    private val post = instaDB.postDao().getPosts()
    private var comment = ArrayList<Comment>()
    private var reply = ArrayList<Reply>()

    interface MyItemClickListener{
        fun onShowComment(postIdx : Int)
        fun onWriteComment(postIdx : Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    private fun addToLikeTable(postIdx : Int) {  // 좋아요
        instaDB.postDao().addLikedPost(LikedPost(postIdx, myIdx))
    }

    private fun deleteFromLikeTable(postIdx: Int) {  // 좋아요 취소
        instaDB.postDao().deleteLikedPost(postIdx, myIdx)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 게시물에 있는 댓글 불러오기
        comment.clear()
        comment.addAll(instaDB.CommentDao().getPostComments(post[position].postIdx))

        reply.clear()
        reply.addAll(instaDB.CommentDao().getPostReplies(post[position].postIdx))

        // 해당 position에 대한 데이터를 binding
        holder.bind(post[position])

        // click listener
        // 좋아요 관련
        holder.binding.itemPostLikeIv.setOnClickListener {
            if(instaDB.postDao().checkLikedPost(myIdx, post[position].postIdx).isEmpty()) {  // 좋아요가 안되어있는 경우
                addToLikeTable(post[position].postIdx)
                holder.binding.itemPostLikeIv.setImageResource(R.drawable.ic_filled_heart)
            }
            else {  // 좋아요를 한 경우
                deleteFromLikeTable(post[position].postIdx)
                holder.binding.itemPostLikeIv.setImageResource(R.drawable.ic_heart)
            }
        }

        // 댓글 모두 보기, 댓글 아이콘을 누르면 댓글 창으로 넘어가도록
        holder.binding.itemPostShowAllCommentTv.setOnClickListener {
            mItemClickListener.onShowComment(post[position].postIdx)
        }

        holder.binding.itemPostCommentIv.setOnClickListener {
            mItemClickListener.onShowComment(post[position].postIdx)
        }

        // 댓글을 달기 위한 bottom sheet dialog를 띄움
        holder.binding.itemPostCommentTv.setOnClickListener {
            mItemClickListener.onWriteComment(post[position].postIdx)
        }
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = post.size

    inner class ViewHolder(val binding : ItemPostBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        @SuppressLint("SetTextI18n")
        fun bind(post: Post){

            // 게시물 올린 사람의 정보 연동
            val user = instaDB.userDao().getUser(post.userIdx)

            binding.itemPostIdTv.text = user.ID
            binding.itemPostProfileIv.setImageResource(user.picture)

            // 게시물 내용 연동
            binding.itemPostIv.setImageResource(post.picture)

            val postText = user.ID + " " + post.text  // 텍스트 가져옴
            val spannableString = SpannableString(postText)  //객체 생성

            // 유저 아이디 부분만 두껍게 표시
            val word = user.ID
            val start = postText.indexOf(word)
            val end = start + word.length

            spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.itemPostTextTv.text = spannableString

            // 내 기준 댓글 연동
            binding.itemPostShowAllCommentTv.text = "댓글 " + (comment.size + reply.size) + "개 모두 보기"
            binding.itemPostMyProfileIv.setImageResource(instaDB.userDao().getUserPicture(myIdx))

            // 좋아요한 게시물은 하트 채우기
            if(instaDB.postDao().checkLikedPost(myIdx, post.postIdx).isEmpty()) {  // 좋아요가 안되어있는 경우
                binding.itemPostLikeIv.setImageResource(R.drawable.ic_heart)
            }
            else {  // 좋아요를 한 경우
                binding.itemPostLikeIv.setImageResource(R.drawable.ic_filled_heart)
            }
        }
    }
}