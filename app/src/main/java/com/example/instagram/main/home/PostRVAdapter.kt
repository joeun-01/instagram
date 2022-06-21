package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.data.*
import com.example.instagram.databinding.ItemPostBinding
import com.example.instagram.room.InstagramDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PostRVAdapter(context : Context, private val myInfo : UserDB?) : RecyclerView.Adapter<PostRVAdapter.ViewHolder>() {
    private val instaDB = InstagramDatabase.getInstance(context)!!

    private val post = arrayListOf<PostDB>()
    private var comment = ArrayList<Comment>()
    private var reply = ArrayList<Reply>()

    // 유저 파이어베이스
    private val database = Firebase.database
    private val userRef = database.getReference("user")

    interface MyItemClickListener{
        fun onShowComment(postIdx : Int)
        fun onWriteComment(postIdx : Int)
        fun onShowShare(postIdx: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewPost(post : PostDB) {
        // 리사이클러뷰에 들어갈 게시물 추가
        this.post.add(post)
        notifyDataSetChanged()
        Log.d("SUCCESS-POST", this.post.toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearNewPost() {
        this.post.clear()
        notifyDataSetChanged()
    }

//    private fun addToLikeTable(postIdx : Int) {  // 좋아요
//        instaDB.postDao().addLikedPost(LikedPost(postIdx, myIdx))
//    }
//
//    private fun deleteFromLikeTable(postIdx: Int) {  // 좋아요 취소
//        instaDB.postDao().deleteLikedPost(postIdx, myIdx)
//    }

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
//        holder.binding.itemPostLikeIv.setOnClickListener {
//            if(instaDB.postDao().checkLikedPost(myIdx, post[position].postIdx).isEmpty()) {  // 좋아요가 안되어있는 경우
//                addToLikeTable(post[position].postIdx)
//                holder.binding.itemPostLikeIv.setImageResource(R.drawable.ic_filled_heart)
//            }
//            else {  // 좋아요를 한 경우
//                deleteFromLikeTable(post[position].postIdx)
//                holder.binding.itemPostLikeIv.setImageResource(R.drawable.ic_heart)
//            }
//        }

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

        // 공유 아이콘을 누르면 share bottom sheet dialog를 띄움
        holder.binding.itemPostShareIv.setOnClickListener {
            mItemClickListener.onShowShare(post[position].postIdx)
        }
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = post.size

    inner class ViewHolder(val binding : ItemPostBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        @SuppressLint("SetTextI18n")
        fun bind(post: PostDB){
            // 게시물에 맞는 유저 정보 불러오기
            var user : UserDB

            userRef.child(post.uid!!).get().addOnSuccessListener {
                user = it.getValue(UserDB::class.java)!!

                // 스토리 화면에 보이는 프사, 아이디 연동
                binding.itemPostProfileIv.setImageResource(user.picture)
                binding.itemPostIdTv.text = user.ID

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

                Log.d("SUCCESS-USER", user.toString())

            }.addOnFailureListener {
                Log.d("FAIL-STORY", "유저 데이터를 받아오지 못했습니다")
            }

            // 내 기준 댓글 연동
            binding.itemPostShowAllCommentTv.text = "댓글 " + (comment.size + reply.size) + "개 모두 보기"
            binding.itemPostMyProfileIv.setImageResource(myInfo!!.picture)

//            // 좋아요한 게시물은 하트 채우기
//            if(instaDB.postDao().checkLikedPost(myIdx, post.postIdx).isEmpty()) {  // 좋아요가 안되어있는 경우
//                binding.itemPostLikeIv.setImageResource(R.drawable.ic_heart)
//            }
//            else {  // 좋아요를 한 경우
//                binding.itemPostLikeIv.setImageResource(R.drawable.ic_filled_heart)
//            }
        }
    }
}