package com.example.instagram.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.ItemShopWishBinding

class ShopWishRVAdapter(private val wishList: ArrayList<WishItem>): RecyclerView.Adapter<ShopWishRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemShopWishBinding =
            ItemShopWishBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopWishRVAdapter.ViewHolder, position: Int) {
        holder.bind(wishList[position])
    }

    override fun getItemCount(): Int = wishList.size

    inner class ViewHolder(private val binding: ItemShopWishBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(wish: WishItem) {
            binding.itemWishIv.setImageResource(wish.shopImg!!)
            binding.itemWishStoreTv.text = wish.store.toString()
            binding.itemWishTv.text = wish.name.toString()
        }
    }
}