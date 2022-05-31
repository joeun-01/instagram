package com.example.instagram.main.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instagram.main.home.HomeFragment

class ProfileVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            // 여기에 들어간 Fragment는 나중에 만들어서 수정해주면 됩니닷
            0 -> GridPostFragment()
            else -> HomeFragment()
        }
    }
}