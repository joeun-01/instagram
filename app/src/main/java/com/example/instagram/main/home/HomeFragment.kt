package com.example.instagram.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.data.User
import com.example.instagram.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private var profile = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 예시 용 더미데이터
        profile.apply {
            add(User("jjy_joeun", "정조은", R.drawable.profile_ex1))
            add(User("second", "몰라", R.drawable.profile_ex2))
            add(User("third", "음음", R.drawable.profile_ex3))
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initRecyclerView()
    }

    private fun initRecyclerView() {  // RecycleView 연결
        val storyRVAdapter = StoryRVAdapter(profile)
        binding.homeFeedStoryRv.adapter = storyRVAdapter
        binding.homeFeedStoryRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        Log.d("profile 확인", profile.toString())
    }
}