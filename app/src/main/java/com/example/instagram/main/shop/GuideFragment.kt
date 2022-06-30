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
import com.example.instagram.databinding.FragmentShopBinding
import com.example.instagram.main.MainActivity


class GuideFragment : Fragment() {

    private lateinit var binding: FragmentGuideBinding
    private var guideDatas = ArrayList<Guide>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuideBinding.inflate(inflater, container, false)


        binding.guideRv.layoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        val guideRVAdapter = GuideRVAdapter(guideDatas)
        binding.guideRv.adapter = guideRVAdapter

        /*guideRVAdapter.setMyItemClickListener(object : GuideRVAdapter.MyItemClickListener {
            override fun onItemClick(){
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, ShopItemFragment()).commitAllowingStateLoss()
            }
        })*/

        binding.guideWishIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, WishlistFragment()).addToBackStack(null).commitAllowingStateLoss()

        }

        binding.backIb.setOnClickListener {
            (context as MainActivity).onBackPressed()
        }

        return binding.root
    }

}