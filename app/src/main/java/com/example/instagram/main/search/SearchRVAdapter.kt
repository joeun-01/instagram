package com.example.instagram.main.search

import android.annotation.SuppressLint
import android.util.Log
import com.example.instagram.databinding.ItemSearchimgBinding


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.data.PostDB
import com.example.instagram.data.SearchDB
import com.example.instagram.data.UserDB
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SearchRVAdapter(private val myInfo : UserDB?, private val myUid : String?): RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {

    private val search = arrayListOf<SearchDB>()

    private val database = Firebase.database

    interface MyItemClickListener{
        // click function
        fun onShowSearchtab(search: SearchDB)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addNewSearch(search: SearchDB) {
        // 리사이클러뷰에 들어갈 게시물 추가
        this.search.add(search)
        notifyDataSetChanged()
        Log.d("SUCCESS-POST", this.search.toString())
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSearchimgBinding =
            ItemSearchimgBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(search[position])

        holder.binding.itemSearchImg.setOnClickListener {
            // 게시물 누르면 탐색 탭 나오게
            mItemClickListener.onShowSearchtab(search[position])
        }
    }

    override fun getItemCount(): Int =search.size

    inner class ViewHolder(val binding: ItemSearchimgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(search: SearchDB) {

            binding.itemSearchImg.setImageResource(search.picture)

        }
    }

}