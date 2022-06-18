package com.example.instagram.main.reels


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentReelsBinding


class ReelsFragment: Fragment() {

    lateinit var binding: FragmentReelsBinding
    private  var reelsDatas = ArrayList<ReelsItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReelsBinding.inflate(inflater, container, false)


        //더미데이터
        reelsDatas.apply {
            add(ReelsItem("joeun",R.drawable.profile_ex1,"조은 하루", R.drawable.reels_dummyimg2))
            add(ReelsItem("blue",R.drawable.profile_ex2,"블루야 왜 블루",R.drawable.reels_dummyimg))
            add(ReelsItem("dobby",R.drawable.profile_ex3,"dobby is free",R.drawable.reels_dummyimg2))
            add(ReelsItem("ally",R.drawable.profile_ex1,"zzzzzzzzzzzzzzzzzz",R.drawable.reels_dummyimg))

        }

        val reelsRVAdapter = ReelsRVAdapter(reelsDatas)
        binding.reelsRv.adapter = reelsRVAdapter
        binding.reelsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)


        return binding.root

    }



}