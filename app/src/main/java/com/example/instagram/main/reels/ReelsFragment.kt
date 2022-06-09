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
        clickListener()

        //더미데이터
        reelsDatas.apply {
            add(ReelsItem(0, R.drawable.reels_dummyimg2))
            add(ReelsItem(0,R.drawable.reels_dummyimg))

        }

        val reelsRVAdapter = ReelsRVAdapter(reelsDatas)
        binding.reelsRv.adapter = reelsRVAdapter
        binding.reelsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)


        return binding.root

    }

    //하트 누를 때 돔작
    private fun setToggleStatus(click: Boolean) {
        if (click) {
            binding.reelsHeartIv.visibility = View.VISIBLE
            binding.reelsHeartredIv.visibility = View.GONE
        } else {
            binding.reelsHeartIv.visibility = View.GONE
            binding.reelsHeartredIv.visibility = View.VISIBLE
        }
    }

    private fun clickListener(){
        binding.reelsHeartIv.setOnClickListener {
            setToggleStatus(false)
        }
        binding.reelsHeartredIv.setOnClickListener {
            setToggleStatus(true)
        }
    }


}