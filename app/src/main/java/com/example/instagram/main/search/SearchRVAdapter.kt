package com.example.instagram.main.search

import com.example.instagram.databinding.ItemSearchimgBinding


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



class SearchRVAdapter(private val searchList: ArrayList<SearchItem>): RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSearchimgBinding =
            ItemSearchimgBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(searchList[position])
    }

    override fun getItemCount(): Int =searchList.size

    inner class ViewHolder(private val binding: ItemSearchimgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(Img: SearchItem) {

            binding.itemSearchImg.setImageResource(Img.SearchImg!!)

        }
    }

}