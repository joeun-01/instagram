package com.example.instagram.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram.R
import com.example.instagram.data.Post
import com.example.instagram.databinding.FragmentGridPostBinding
import java.util.ArrayList

class GridPostFragment : Fragment() {
    lateinit var binding : FragmentGridPostBinding
    private var post = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGridPostBinding.inflate(inflater, container, false)

        post.apply {
            add(Post(R.drawable.profile_ex1, "사진 돌려막기 1"))
            add(Post(R.drawable.profile_ex2,"사진 돌려막기 2"))
            add(Post(R.drawable.profile_ex3, "사진 돌려막기 3"))
        }

        val gridPostRVAdapter = GridPostRVAdapter(post)
        binding.profilePostGridRv.adapter = gridPostRVAdapter
        binding.profilePostGridRv.layoutManager = GridLayoutManager(context, 3)

        return binding.root
    }
}