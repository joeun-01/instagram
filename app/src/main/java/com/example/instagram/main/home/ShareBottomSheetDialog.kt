package com.example.instagram.main.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.databinding.SharePostBottomSheetDialogBinding
import com.example.instagram.room.InstagramDatabase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShareBottomSheetDialog : BottomSheetDialogFragment() {
    lateinit var binding : SharePostBottomSheetDialogBinding

    lateinit var instaDB : InstagramDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SharePostBottomSheetDialogBinding.inflate(inflater, container, false)

        instaDB = InstagramDatabase.getInstance(requireContext())!!

        // 유저 리사이클러뷰 연결
        val shareRVAdapter = ShareRVAdapter(requireContext(), getMyIdx())
        binding.sharePostUserRv.adapter = shareRVAdapter
        binding.sharePostUserRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 내 정보가 무조건 맨 위에 떠야함
        shareRVAdapter.clearInfo()
        shareRVAdapter.addMyInfo(instaDB.userDao().getUser(getMyIdx()))
        shareRVAdapter.addOthersInfo(instaDB.userDao().getOthers(getMyIdx()))

        return binding.root
    }

    private fun getMyIdx(): Int {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        return userSP.getInt("userIdx", 0)
    }
}