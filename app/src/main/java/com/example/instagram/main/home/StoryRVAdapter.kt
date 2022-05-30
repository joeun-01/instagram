package com.example.instagram.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.User
import com.example.instagram.databinding.ItemHomeStoryBinding

class StoryRVAdapter(private var profile: ArrayList<User>) : RecyclerView.Adapter<StoryRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        // click function
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
        holder.bind(profile[position])

        // click listener
    }

    // data set의 크기를 알려줌
    override fun getItemCount(): Int = profile.size

    inner class ViewHolder(private val binding : ItemHomeStoryBinding) : RecyclerView.ViewHolder(binding.root){
        // ItemView를 잡아주는 ViewHolder
        fun bind(profile: User){
            binding.homeStoryPictureIv.setImageResource(profile.picture)
            binding.homeStoryNameTv.text = profile.ID
        }
    }
}