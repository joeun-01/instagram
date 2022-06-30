package com.example.instagram.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram.R
import com.example.instagram.databinding.FragmentCreateBinding
import com.example.instagram.databinding.FragmentListBinding
import com.example.instagram.main.MainActivity
import com.example.instagram.main.home.AddPostActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateBottomSheetDialog: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(inflater, container, false)

        binding.createPostTv.setOnClickListener {
            startActivity(Intent(activity, AddPostActivity::class.java))
        }

        return binding.root
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

}