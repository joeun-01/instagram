package com.example.instagram.main.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentShopItemBinding
import com.example.instagram.main.MainActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class ShopItemFragment : Fragment() {

    private lateinit var binding: FragmentShopItemBinding
    private val database = Firebase.database
    private val shopDB = database.getReference("shop")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopItemBinding.inflate(inflater, container, false)

        val gson = Gson()
        val shopJson = arguments?.getString("shopItem")
        val shop = gson.fromJson(shopJson, ShopItem::class.java)

        setInit(shop)

       with(binding){
            itemContentsEl.setOnExpandListener {
                if(it){
                    Toast.makeText(context, "expanded", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "collapse", Toast.LENGTH_SHORT).show()
                }
            }

            itemContentsEl.parentLayout.setOnClickListener{itemContentsEl.toggleLayout()}

            itemContents2El.parentLayout.setOnClickListener { itemContents2El.toggleLayout() }
        }

        //뒤로가기
        binding.backIb.setOnClickListener {
            (context as MainActivity).onBackPressed()
        }



        return binding.root
    }

    private fun setInit(shop: ShopItem) {
        binding.brandIdTv.text = shop.brandId.toString()
        binding.shippingBrandNameTv.text = shop.brandName.toString()
        binding.itemDetailIv.setImageResource(shop.shopImg!!)
        binding.itemDetailPriceTv.text = shop.price.toString()
        binding.itemDetailNameTv.text = shop.itemName.toString()
        if(shop.wish == 1){
            binding.itemDetailBookmarkIv.setImageResource(R.drawable.ic_bookmark_selected)
        }

    }
}