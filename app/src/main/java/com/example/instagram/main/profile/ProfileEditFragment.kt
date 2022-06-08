package com.example.instagram.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.databinding.FragmentProfileEditBinding
import com.example.instagram.main.MainActivity

class ProfileEditFragment() : Fragment() {
    private lateinit var binding : FragmentProfileEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.editCancelTv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.homeFragment,ProfileFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }
}