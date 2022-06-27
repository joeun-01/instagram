package com.example.instagram.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.ItemShopBinding


class ShopRVAdapter(private val shopList: ArrayList<ShopItem>): RecyclerView.Adapter<ShopRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemShopBinding =
            ItemShopBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopRVAdapter.ViewHolder, position: Int) {
        holder.bind(shopList[position])

    }

    override fun getItemCount(): Int = shopList.size

    inner class ViewHolder(private val binding: ItemShopBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(Img: ShopItem) {
            binding.shopItemIv.setImageResource(Img.shopImg!!)


        }
    }
}