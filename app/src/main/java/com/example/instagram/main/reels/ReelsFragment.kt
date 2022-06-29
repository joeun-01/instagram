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
            add(ReelsItem("tama",R.drawable.profile_ex3,"벤틀리",R.raw.video1))
            add(ReelsItem("luna",R.drawable.profile_ex2,"졸귀",R.raw.video4))

        }

        val reelsRVAdapter = ReelsRVAdapter(reelsDatas)
        binding.reelsRv.adapter = reelsRVAdapter
        binding.reelsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)


        return binding.root

    }



}