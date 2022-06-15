package com.example.instagram.main.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentShopBinding
import com.example.instagram.main.MainActivity

class ShopFragment : Fragment() {

    private lateinit var binding: FragmentShopBinding
    private var shopDatas = ArrayList<ShopItem>()
    private var shopWishDatas = ArrayList<WishItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopBinding.inflate(inflater, container, false)

        //shopping dummy data
        shopDatas.apply {
            add(ShopItem(0, R.drawable.shop_item1, 0))
            add(ShopItem(1, R.drawable.shop_item2, 0))
            add(ShopItem(2, R.drawable.shop_item3, 0))
            add(ShopItem(3, R.drawable.shop_item4, 0))
            add(ShopItem(4, R.drawable.shop_item5, 0))
            add(ShopItem(5, R.drawable.shop_item6, 0))
        }

        //shopping wish list dummy data
        shopWishDatas.apply {
            add(WishItem(0, R.drawable.shop_item3, "나일론 호보백", "nothingwritten"))
            add(WishItem(1, R.drawable.shop_item4, "penny bag", "alexanderwang"))
            add(WishItem(2, R.drawable.shop_item5, "Summer pants", "treemingbird"))
            add(WishItem(3, R.drawable.shop_item6, "Lap Skirt White", "treemingbird"))
        }

        binding.shopListRv.layoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        val shopRVAdapter = ShopRVAdapter(shopDatas)
        binding.shopListRv.adapter = shopRVAdapter


        binding.shopWishlistRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val shopWishRVAdapter = ShopWishRVAdapter(shopWishDatas)
        binding.shopWishlistRv.adapter = shopWishRVAdapter


        binding.shopWishIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, WishlistFragment()).commitAllowingStateLoss()
        }


        /*val bottomSheet = ShopDialogFragment()
        binding.shopListIv.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.main_frm, bottomSheet)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }*/

        return binding.root
    }
}