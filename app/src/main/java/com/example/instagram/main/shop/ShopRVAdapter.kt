package com.example.instagram.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.databinding.ItemShopBinding
import com.example.instagram.main.MainActivity
import com.example.instagram.main.search.SearchItem


class ShopRVAdapter(private val shopList: ArrayList<ShopItem>): RecyclerView.Adapter<ShopRVAdapter.ViewHolder>() {

    private var activity: MainActivity? = null


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

            binding.shopItemIv.setOnClickListener {
               /* (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, ShopItemFragment()).commitAllowingStateLoss()*/
            }
        }
    }
}