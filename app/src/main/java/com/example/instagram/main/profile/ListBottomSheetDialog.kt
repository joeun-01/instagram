package com.example.instagram.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram.R
import com.example.instagram.databinding.FragmentListBinding
import com.example.instagram.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ListBottomSheetDialog: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        binding.listSettingTv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, SettingsFragment()).commitAllowingStateLoss()

            dismiss()
        }

        return binding.root
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

}