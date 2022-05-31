package com.example.instagram.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.data.Post
import com.example.instagram.data.User
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.room.InstagramDatabase

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private lateinit var instaDB : InstagramDatabase

    private var profile = ArrayList<User>()
    private var post = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        instaDB = InstagramDatabase.getInstance(requireContext())!!

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


        post.addAll(instaDB.postDao().getPosts())

        val postRVAdapter = PostRVAdapter(requireContext(), post)
        binding.homeFeedPostRv.adapter = postRVAdapter
        binding.homeFeedPostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        Log.d("profile 확인", profile.toString())

        Log.d("post 확인", post.toString())
    }
}