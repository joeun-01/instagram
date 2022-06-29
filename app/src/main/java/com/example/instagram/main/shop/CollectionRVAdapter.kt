package com.example.instagram.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.ItemCollectionBinding
import com.example.instagram.databinding.ItemGuideBinding

class CollectionRVAdapter(private val collectionList: ArrayList<Collection>): RecyclerView.Adapter<CollectionRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick()
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCollectionBinding =
            ItemCollectionBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionRVAdapter.ViewHolder, position: Int) {
        holder.bind(collectionList[position])

    }

    override fun getItemCount(): Int = collectionList.size

    inner class ViewHolder(private val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(collect: Collection) {
            binding.collectionProfileIv.setImageResource(collect.shopImg!!)
            binding.collectionImgIv.setImageResource(collect.collectionImg!!)
            binding.collectionIdTv.text = collect.brandId.toString()
            binding.collectionNameTv.text = collect.collectionName.toString()
            binding.collectionTotalTv.text = collect.totalItem.toString()


            /*binding.guideCv.setOnClickListener {
                mItemClickListener.onItemClick()
            }*/

        }
    }
}