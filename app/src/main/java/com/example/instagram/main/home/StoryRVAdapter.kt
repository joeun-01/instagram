package com.example.instagram.main.home

import android.content.Context
import android.content.pm.InstallSourceInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.Story
import com.example.instagram.data.User
import com.example.instagram.databinding.ItemHomeStoryBinding
import com.example.instagram.room.InstagramDatabase

class StoryRVAdapter(context : Context, myIdx: Int) : RecyclerView.Adapter<StoryRVAdapter.ViewHolder>() {
    private var instaDB = InstagramDatabase.getInstance(context)!!
    private var story = instaDB.storyDao().getOthersStory(myIdx)

    interface MyItemClickListener{
        // click function
        fun onShowStory(userIdx : Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemHomeStoryBinding = ItemHomeStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  // ViewHolder를 생성
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 position에 대한 데이터를 binding
        holder.bind(story[position])

        // click listener
        holder.binding.homeStoryLy.setOnClickListener {
            // 스토리를 누르면 자세히 볼 수 있도록
            mItemClickListener.onShowStory(story[position].userIdx)
        }
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = story.size

    inner class ViewHolder(val binding : ItemHomeStoryBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        fun bind(story: Story){
            // 스토리 화면에 보이는 프사, 아이디 연동
            binding.homeStoryPictureIv.setImageResource(instaDB.userDao().getUserPicture(story.userIdx))
            binding.homeStoryNameTv.text = instaDB.userDao().getUserID(story.userIdx)
        }
    }
}