package com.example.instagram.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.ItemWishlistBinding

class WishlistRVAdapter (private val wishList: ArrayList<WishItem>): RecyclerView.Adapter<WishlistRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWishlistBinding =
            ItemWishlistBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishlistRVAdapter.ViewHolder, position: Int) {
        holder.bind(wishList[position])
    }

    override fun getItemCount(): Int = wishList.size

    inner class ViewHolder(private val binding: ItemWishlistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(wish: WishItem) {
            binding.itemWishIv.setImageResource(wish.shopImg!!)
            binding.wishlistNameTv.text = wish.name.toString()
            binding.wishlistStoreTv.text = wish.store.toString()
        }
    }
}