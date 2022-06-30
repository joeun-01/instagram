package com.example.instagram.main.shop

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.data.StoryDB
import com.example.instagram.databinding.ItemShopBinding
import com.example.instagram.main.MainActivity
import com.example.instagram.main.search.SearchItem


class ShopRVAdapter(): RecyclerView.Adapter<ShopRVAdapter.ViewHolder>() {
    private var shopList = ArrayList<ShopItem>()


    interface MyItemClickListener{
        fun onItemClick(shop : ShopItem)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addShopItem(shop : ShopItem) {
        Log.d("shop-complete", "성공")
        this.shopList.add(shop)
        notifyDataSetChanged()
        Log.d("Success-Shop", this.shopList.toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearShop() {
        this.shopList.clear()
        notifyDataSetChanged()
    }

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

        fun bind(shop: ShopItem) {
            binding.shopItemIv.setImageResource(shop.shopImg!!)

            binding.shopItemIv.setOnClickListener {
                mItemClickListener.onItemClick(shop)
            }

        }
    }
}