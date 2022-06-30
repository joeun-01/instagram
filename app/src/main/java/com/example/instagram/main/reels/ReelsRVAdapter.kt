package com.example.instagram.main.reels

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.ItemReelsimgBinding

class ReelsRVAdapter(private val reelsList: ArrayList<ReelsItem>): RecyclerView.Adapter<ReelsRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemReelsimgBinding =
            ItemReelsimgBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(reelsList[position])
    }

    override fun getItemCount(): Int =reelsList.size

    inner class ViewHolder(private val binding: ItemReelsimgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(Img: ReelsItem) {
            binding.itemReelsIdTv.text = Img.userId
            binding.itemReelsUserimgIv.setImageResource(Img.userImg!!)
            binding.itemReelsUserimg2Iv.setImageResource(Img.userImg!!)
            binding.itemReelsContentTv.text=Img.content
            //비디오 재생

            val videoUri = Uri.parse( "android.resource://com.example.instagram/"   + Img.ReelsImg!!)

            binding.itemReelsImg.setVideoURI(videoUri)
            binding.itemReelsImg.start()

            //무한 반복
            binding.itemReelsImg.setOnCompletionListener{
                binding.itemReelsImg.start()}


            binding.itemReelsImg.setOnPreparedListener{
                binding.itemReelsImg.start()}

        }

        }

    }

