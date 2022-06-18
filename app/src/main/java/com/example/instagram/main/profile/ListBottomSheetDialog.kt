package com.example.instagram.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentListBinding
import com.example.instagram.main.MainActivity
<<<<<<< HEAD
import com.example.instagram.main.shop.WishlistFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
=======
import com.example.instagram.main.search.SearchListFragment
>>>>>>> 034338c3bbf7aae0f5b9e8bcc09467cac36377f4
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

}