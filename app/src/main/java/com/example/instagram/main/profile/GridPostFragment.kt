package com.example.instagram.main.profile
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.GridLayoutManager
//import com.example.instagram.R
//import com.example.instagram.data.Post
//import com.example.instagram.databinding.FragmentGridPostBinding
//import com.example.instagram.room.InstagramDatabase
//import java.util.ArrayList
//
//class GridPostFragment : Fragment() {
//    lateinit var binding : FragmentGridPostBinding
//
//    private lateinit var instaDB : InstagramDatabase
//    private var post = ArrayList<Post>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentGridPostBinding.inflate(inflater, container, false)
//
//        instaDB = InstagramDatabase.getInstance(requireContext())!!
//
//        post.addAll(instaDB.postDao().getPosts())
//
//        val gridPostRVAdapter = GridPostRVAdapter(post)
//        binding.profilePostGridRv.adapter = gridPostRVAdapter
//        binding.profilePostGridRv.layoutManager = GridLayoutManager(context, 3)
//
//        return binding.root
//    }
//}