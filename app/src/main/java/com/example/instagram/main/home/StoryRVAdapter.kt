package com.example.instagram.main.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.data.StoryDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.ItemHomeStoryBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StoryRVAdapter(private val myUid: String?) : RecyclerView.Adapter<StoryRVAdapter.ViewHolder>() {
    private var story = arrayListOf<StoryDB>()

    // 유저 파이어베이스
    private val database = Firebase.database
    private val userRef = database.getReference("user")

    interface MyItemClickListener{
        // click function
        fun onShowStory(userIdx : Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewStory(story : StoryDB) {
        // 리사이클러뷰에 들어갈 스토리 추가
        this.story.add(story)
        Log.d("SUCCESS-STORY", this.story.toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewStoryToFirst(story : StoryDB) {
        this.story.add(0, story)
        Log.d("SUCCESS-STORY", this.story.toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearNewStory() {
        this.story.clear()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemHomeStoryBinding = ItemHomeStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 position에 대한 데이터를 binding
        holder.bind(story[position])

        // click listener
//        holder.binding.homeStoryLy.setOnClickListener {
//            // 스토리를 누르면 자세히 볼 수 있도록
//            mItemClickListener.onShowStory(story[position].userIdx)
//        }
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = story.size

    inner class ViewHolder(val binding : ItemHomeStoryBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        fun bind(story: StoryDB){
            // 스토리에 맞는 유저 정보 불러오기
            var user : UserDB

            Log.d("SUCCESS-USER", story.toString())

            userRef.child(story.uid!!).get().addOnSuccessListener {
                user = it.getValue(UserDB::class.java)!!

                // 스토리 화면에 보이는 프사, 아이디 연동
                binding.homeStoryPictureIv.setImageResource(user.picture)
                binding.homeStoryNameTv.text = user.ID

                Log.d("SUCCESS-USER", user.toString())

            }.addOnFailureListener {
                Log.d("FAIL-STORY", "유저 데이터를 받아오지 못했습니다")
            }

            // 내 스토리 & 올린 스토리가 없을 경우에는 더하기 버튼 띄우기
            if(story.uid == myUid && story.picture == 0) {
                binding.homeStoryAddBtn.visibility = View.VISIBLE
                binding.homeStoryPictureIv.background = null
            }
            else {
                binding.homeStoryAddBtn.visibility = View.GONE
                binding.homeStoryPictureIv.setBackgroundResource(R.drawable.story_background)
            }
        }
    }
}