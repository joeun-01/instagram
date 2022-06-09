package com.example.instagram.main.reels

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

            binding.itemReelsImg.setImageResource(Img.ReelsImg!!)

        }
    }

}