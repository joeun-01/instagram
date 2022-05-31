package com.example.instagram.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    private var tab = arrayListOf(R.drawable.ic_grid, R.drawable.ic_tag)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // ViewPager 연결
        val profileVPAdapter = ProfileVPAdapter(this)
        binding.profilePostVp.adapter = profileVPAdapter

        // ViewPager와 TabLayout 연결
        TabLayoutMediator(binding.profilePostTb, binding.profilePostVp) {
            tab, position ->
            tab.setIcon(this.tab[position])
        }.attach()

        return binding.root
    }
}