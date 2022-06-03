package com.example.instagram.main.home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.room.InstagramDatabase
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private lateinit var instaDB : InstagramDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        instaDB = InstagramDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initRecyclerView()
    }

    private fun initRecyclerView() {  // RecycleView 연결
        val storyRVAdapter = StoryRVAdapter(requireContext())
        binding.homeFeedStoryRv.adapter = storyRVAdapter
        binding.homeFeedStoryRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val postRVAdapter = PostRVAdapter(requireContext(), getMyIdx())
        binding.homeFeedPostRv.adapter = postRVAdapter
        binding.homeFeedPostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        postRVAdapter.setMyItemClickListener(object : PostRVAdapter.MyItemClickListener {
            override fun showComment(postIdx : Int) {
                showAllComment(postIdx)
            }

            override fun writeComment(postIdx: Int) {
                writeMyComment(postIdx)
            }
        })
    }

    private fun getMyIdx(): Int {
        val userSP = requireActivity().getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getInt("userIdx", 0)
    }

    private fun writeMyComment(postIdx: Int) {
        val dialogView = layoutInflater.inflate(R.layout.write_reply_bottom_sheet_dialog, null)
        val dialog = BottomSheetDialog(requireContext())

        dialog.setContentView(dialogView)

        dialog.show()
    }

    private fun showAllComment(postIdx : Int) {
        val postSP = requireActivity().getSharedPreferences("post", MODE_PRIVATE)
        val postEditor = postSP.edit()

        postEditor.putInt("postIdx", postIdx)
        postEditor.apply()

        startActivity(Intent(requireContext(), CommentActivity::class.java))
    }

}