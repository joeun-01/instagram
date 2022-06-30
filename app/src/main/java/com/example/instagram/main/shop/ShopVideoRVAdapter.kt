package com.example.instagram.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.ItemGuideBinding
import com.example.instagram.databinding.ItemVideoBinding

class ShopVideoRVAdapter(private val videoList: ArrayList<ShopVideo>): RecyclerView.Adapter<ShopVideoRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick()
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemVideoBinding =
            ItemVideoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopVideoRVAdapter.ViewHolder, position: Int) {
        holder.bind(videoList[position])

    }

    override fun getItemCount(): Int = videoList.size

    inner class ViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: ShopVideo) {
            binding.videoIv.setImageResource(video.videoImg!!)
            binding.videoIdTv.text = video.userId.toString()

            /*binding.videoCv.setOnClickListener {
                mItemClickListener.onItemClick()
            }*/

        }
    }

}