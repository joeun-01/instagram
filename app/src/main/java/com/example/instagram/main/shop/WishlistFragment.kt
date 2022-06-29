package com.example.instagram.main.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentWishlistBinding
import com.example.instagram.main.MainActivity

class WishlistFragment : Fragment() {
    private lateinit var binding: FragmentWishlistBinding
    private var wishDatas = ArrayList<WishItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishlistBinding.inflate(inflater, container, false)

        binding.backIb.setOnClickListener {
            //activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        binding.wishlistCartIb.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, CartFragment()).addToBackStack(null).commitAllowingStateLoss()
        }

        wishDatas.apply {
            add(WishItem(0, R.drawable.shop_item3, "나일론 호보백", "nothingwritten"))
            add(WishItem(1, R.drawable.shop_item4, "penny bag", "alexanderwang"))
            add(WishItem(2, R.drawable.shop_item5, "Summer pants", "treemingbird"))
            add(WishItem(3, R.drawable.shop_item6, "Lap Skirt White", "treemingbird"))
        }


        binding.wishlistRv.layoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        val wishlistRVAdapter = WishlistRVAdapter(wishDatas)
        binding.wishlistRv.adapter = wishlistRVAdapter


        binding.backIb.setOnClickListener {
            (context as MainActivity).onBackPressed()
        }

        return binding.root
    }
}