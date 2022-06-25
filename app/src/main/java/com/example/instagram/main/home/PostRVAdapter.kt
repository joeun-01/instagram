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
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PostRVAdapter(private val myInfo : UserDB?, private val myUid : String?) : RecyclerView.Adapter<PostRVAdapter.ViewHolder>() {
    private val post = arrayListOf<PostDB>()

    // 유저 파이어베이스
    private val database = Firebase.database
    private val userRef = database.getReference("user")

    // 댓글 파이어베이스
    private val commentRef = database.getReference("comment")
    private var comment = 0

    // 답글 파이어베이스
    private val replyRef = database.getReference("reply")
    private var reply = 0

    // 게시물 좋아요 파이어베이스
    private val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val likeRef = database.getReference("likedPost")
    private var liked = false

    interface MyItemClickListener {
        fun onShowComment(post: PostDB)
        fun onWriteComment(postIdx: Int)
        fun onShowShare(postIdx: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewPost(post: PostDB) {
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

    private fun addToLikeTable(postIdx: Int) {  // 좋아요
        mDatabase.child("likedPost").child(postIdx.toString() + myUid)
            .setValue(LikedPostDB(myUid!!, postIdx))
    }

    private fun deleteFromLikeTable(postIdx: Int) {  // 좋아요 취소
        likeRef.child(postIdx.toString() + myUid).removeValue()
    }

    private fun getComments(postIdx: Int) {
        // 게시물에 달린 댓글 받아오기
        commentRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    comment = 0

                    for (commentSnapShot in snapshot.children) {
                        val getData = commentSnapShot.getValue(CommentDB::class.java)

                        if (getData != null) {
                            if (getData.postIdx == postIdx) {
                                comment++
                                Log.d("RECYCLER", getData.toString())
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FAIL", "데이터를 불러오지 못했습니다")
            }
        })
    }

    private fun getReplies(postIdx: Int) {
        // 게시물에 달린 댓글 받아오기
        replyRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    reply = 0

                    for (replySnapShot in snapshot.children) {
                        val getData = replySnapShot.getValue(ReplyDB::class.java)

                        if (getData != null) {
                            if (getData.postIdx == postIdx) {
                                reply++
                                Log.d("RECYCLER", getData.toString())
                            }
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FAIL", "데이터를 불러오지 못했습니다")
            }
        })
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPostBinding =
            ItemPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 position에 대한 데이터를 binding
        holder.bind(post[position])

        // click listener
        // 좋아요 관련
        holder.binding.itemPostLikeIv.setOnClickListener {
            if (liked) {  // 좋아요가 되어있는 경우
                deleteFromLikeTable(post[position].postIdx)
                holder.binding.itemPostLikeIv.setImageResource(R.drawable.ic_heart)
                liked = false
            } else {  // 좋아요가 되어있지 않은 경우
                addToLikeTable(post[position].postIdx)
                holder.binding.itemPostLikeIv.setImageResource(R.drawable.ic_filled_heart)
                liked = true
            }
        }

        // 댓글 모두 보기, 댓글 아이콘을 누르면 댓글 창으로 넘어가도록
        holder.binding.itemPostShowAllCommentTv.setOnClickListener {
            mItemClickListener.onShowComment(post[position])
        }

        holder.binding.itemPostCommentIv.setOnClickListener {
            mItemClickListener.onShowComment(post[position])
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

            // 게시물에 있는 댓글 불러오기
            getComments(post.postIdx)
            getReplies(post.postIdx)

            Log.d("SIZE-CHECK", "${post.postIdx} : $comment + $reply")

            // 내 기준 댓글 연동
            binding.itemPostShowAllCommentTv.text = "댓글 " + (comment + reply) + "개 모두 보기"
            binding.itemPostMyProfileIv.setImageResource(myInfo!!.picture)

            // 좋아요한 게시물은 하트 채우기
            likeRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (likeSnapShot in snapshot.children){
                            val getData = likeSnapShot.getValue(LikedPostDB::class.java)

                            if (getData != null) {
                                if(getData.postIdx == post.postIdx) {
                                    liked = true
                                    Log.d("CHECKING", getData.postIdx.toString())
                                }
                            }
                        }

                        if(liked) {  // 좋아요가 되어있는 경우
                            binding.itemPostLikeIv.setImageResource(R.drawable.ic_filled_heart)
                        }
                        else {
                            binding.itemPostLikeIv.setImageResource(R.drawable.ic_heart)
                        }

                        liked = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("FAIL", "데이터를 불러오지 못했습니다")
                }
            })
        }
    }
}