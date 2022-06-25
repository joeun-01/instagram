package com.example.instagram.main.direct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.ItemMessageBinding

class DirectRVAdapter (private val directList: ArrayList<Direct>): RecyclerView.Adapter<DirectRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick()
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMessageBinding =
            ItemMessageBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DirectRVAdapter.ViewHolder, position: Int) {
        holder.bind(directList[position])

    }

    override fun getItemCount(): Int = directList.size

    inner class ViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(direct: Direct) {



        }
    }
}