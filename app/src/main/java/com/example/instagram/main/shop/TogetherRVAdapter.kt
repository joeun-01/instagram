package com.example.instagram.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.ItemBrandBinding

class TogetherRVAdapter (private val togetherList: ArrayList<Together>): RecyclerView.Adapter<TogetherRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick()
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBrandBinding =
            ItemBrandBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TogetherRVAdapter.ViewHolder, position: Int) {
        holder.bind(togetherList[position])

    }

    override fun getItemCount(): Int = togetherList.size

    inner class ViewHolder(private val binding: ItemBrandBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(together: Together) {
            binding.wishlistPriceTv.text = together.price.toString()
            binding.itemWishIv.setImageResource(together.shopImg!!)
            binding.wishlistNameTv.text = together.itemName.toString()

            /*binding.guideCv.setOnClickListener {
                mItemClickListener.onItemClick()
            }*/

        }
    }
}