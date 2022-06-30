package com.example.instagram.main.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentGuideBinding
import com.example.instagram.databinding.FragmentShopVideoBinding
import com.example.instagram.main.MainActivity

class ShopVideoFragment : Fragment() {

    private lateinit var binding: FragmentShopVideoBinding
    private var videoDatas = ArrayList<ShopVideo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopVideoBinding.inflate(inflater, container, false)


        binding.videoRv.layoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        val shopVideoRVAdapter = ShopVideoRVAdapter(videoDatas)
        binding.videoRv.adapter = shopVideoRVAdapter

        /*guideRVAdapter.setMyItemClickListener(object : GuideRVAdapter.MyItemClickListener {
            override fun onItemClick(){
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, ShopItemFragment()).commitAllowingStateLoss()
            }
        })*/


        binding.videoWishIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, WishlistFragment()).addToBackStack(null).commitAllowingStateLoss()
        }

        videoDatas.apply {
            add(ShopVideo(R.drawable.guide, "doremifa"))
            add(ShopVideo(R.drawable.guide, "doremifa"))
            add(ShopVideo(R.drawable.guide, "doremifa"))
            add(ShopVideo(R.drawable.guide, "doremifa"))
            add(ShopVideo(R.drawable.guide, "doremifa"))
        }

        binding.backIb.setOnClickListener {
            (context as MainActivity).onBackPressed()
        }

        return binding.root
    }
}